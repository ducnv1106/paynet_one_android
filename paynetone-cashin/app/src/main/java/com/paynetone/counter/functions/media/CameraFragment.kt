package com.paynetone.counter.functions.media

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.res.Configuration
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.extensions.HdrImageCaptureExtender
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paynetone.counter.databinding.FrmCameraBinding
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.util.concurrent.ExecutionException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates
import com.paynetone.counter.R
import com.paynetone.counter.model.Media
import com.paynetone.counter.utils.*
import kotlinx.coroutines.launch

class CameraFragment : BaseFragmentMedia<FrmCameraBinding>(R.layout.frm_camera) {
    // An instance for display manager to get display change callbacks
    private val displayManager by lazy { requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
    private var preview: Preview? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null

    // A lazy instance of the current fragment's view binding
    override val binding: FrmCameraBinding by lazy { FrmCameraBinding.inflate(layoutInflater) }

    private var displayId = -1

    // Selector showing which camera is selected (front or back)
    private var lensFacing = CameraSelector.DEFAULT_BACK_CAMERA

    // Selector showing which flash mode is selected (on, off or auto)
    private var flashMode by Delegates.observable(ImageCapture.FLASH_MODE_OFF) { _, _, new ->
        binding.btnFlash.setImageResource(
            when (new) {
                ImageCapture.FLASH_MODE_ON -> R.drawable.ic_flash_on
                ImageCapture.FLASH_MODE_AUTO -> R.drawable.ic_flash_auto
                else -> R.drawable.ic_flash_off
            }
        )
    }
    val args: CameraFragmentArgs by navArgs()
    private var type : Int = 0


    // Selector showing is grid enabled or not
    private var hasGrid = false

    // Selector showing is hdr enabled or not (will work, only if device's camera supports hdr on hardware level)
    private var hasHdr = false

    /**
     * A display listener for orientation changes that do not trigger a configuration
     * change, for example if we choose to override config change in manifest or for 180-degree
     * orientation changes.
     */
    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit

        @SuppressLint("UnsafeExperimentalUsageError", "UnsafeOptInUsageError")
        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            if (displayId == this@CameraFragment.displayId) {
                preview?.targetRotation = view.display.rotation
                imageCapture?.targetRotation = view.display.rotation
                imageAnalyzer?.targetRotation = view.display.rotation
            }
        } ?: Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.type.let {
            this.type = it

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flashMode = SharedPref.getInstance(requireContext()).getInt(KEY_FLASH,
            ImageCapture.FLASH_MODE_OFF
        )
        hasGrid = SharedPref.getInstance(requireContext()).getBoolean(KEY_GRID, false)
        hasHdr = SharedPref.getInstance(requireContext()).getBoolean(KEY_HDR, false)
        initViews()

        displayManager.registerDisplayListener(displayListener, null)

        binding.run {
            viewFinder.addOnAttachStateChangeListener(object :
                View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View) =
                    displayManager.registerDisplayListener(displayListener, null)

                override fun onViewAttachedToWindow(v: View) =
                    displayManager.unregisterDisplayListener(displayListener)
            })
            btnTakePicture.setOnClickListener { takePicture() }
            btnSwitchCamera.setOnClickListener { toggleCamera() }
            btnGrid.setOnClickListener { toggleGrid() }
            btnFlash.setOnClickListener { selectFlash() }
            btnHdr.setOnClickListener { toggleHdr() }
            btnFlashOff.setOnClickListener { closeFlashAndSelect(ImageCapture.FLASH_MODE_OFF) }
            btnFlashOn.setOnClickListener { closeFlashAndSelect(ImageCapture.FLASH_MODE_ON) }
            btnFlashAuto.setOnClickListener { closeFlashAndSelect(ImageCapture.FLASH_MODE_AUTO) }

//            btnVideoCamera.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_camera_to_video) }
//
//            if (type != DIALOG_AVATAR ){
//                // This swipe gesture adds a fun gesture to switch between video and photo
//                val swipeGestures = SwipeGestureDetector().apply {
//                    setSwipeCallback(right = {
//                        Navigation.findNavController(view).navigate(R.id.action_camera_to_video)
//                    })
//                }
//                val gestureDetectorCompat = GestureDetector(requireContext(), swipeGestures)
//                viewFinder.setOnTouchListener { _, motionEvent ->
//                    if (gestureDetectorCompat.onTouchEvent(motionEvent)) return@setOnTouchListener false
//                    return@setOnTouchListener true
//                }
//            }else{
//                //hide btnVideoCamera
//                binding.btnVideoCamera.visibility = View.GONE
//            }

        }
    }

    /**
     * Create some initial states
     * */
    private fun initViews() {
        binding.btnGrid.setImageResource(if (hasGrid) R.drawable.ic_grid_on else R.drawable.ic_grid_off)
        binding.groupGridLines.visibility = if (hasGrid) View.VISIBLE else View.GONE
        adjustInsets()
    }

    /**
     * This methods adds all necessary margins to some views based on window insets and screen orientation
     * */
    private fun adjustInsets() {
        activity?.window?.fitSystemWindows()
        binding.btnTakePicture.onWindowInsets { view, windowInsets ->
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                view.bottomMargin =
                    windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            else view.endMargin = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).right
        }
    }

    /**
     * Change the facing of camera
     *  toggleButton() function is an Extension function made to animate button rotation
     * */
    @SuppressLint("RestrictedApi")
    fun toggleCamera() = binding.btnSwitchCamera.toggleButton(
        flag = lensFacing == CameraSelector.DEFAULT_BACK_CAMERA,
        rotationAngle = 180f,
        firstIcon = R.drawable.ic_outline_camera_rear,
        secondIcon = R.drawable.ic_outline_camera_front,
    ) {
        lensFacing = if (it) {
            CameraSelector.DEFAULT_BACK_CAMERA
        } else {
            CameraSelector.DEFAULT_FRONT_CAMERA
        }

        startCamera()
    }

    /**
     * Navigate to PreviewFragment
     * */
    private fun openPreview(media: Media) {
        view?.let {
            findNavController().navigate(CameraFragmentDirections.actionCameraToPreview(media))
        }
    }
    /**
     * Show flashlight selection menu by circular reveal animation.
     *  circularReveal() function is an Extension function which is adding the circular reveal
     * */
    private fun selectFlash() = binding.llFlashOptions.circularReveal(binding.btnFlash)

    /**
     * This function is called from XML view via Data Binding to select a FlashMode
     *  possible values are ON, OFF or AUTO
     *  circularClose() function is an Extension function which is adding circular close
     * */
    private fun closeFlashAndSelect(@ImageCapture.FlashMode flash: Int) =
        binding.llFlashOptions.circularClose(binding.btnFlash) {
            flashMode = flash
            binding.btnFlash.setImageResource(
                when (flash) {
                    ImageCapture.FLASH_MODE_ON -> R.drawable.ic_flash_on
                    ImageCapture.FLASH_MODE_OFF -> R.drawable.ic_flash_off
                    else -> R.drawable.ic_flash_auto
                }
            )
            imageCapture?.flashMode = flashMode
            SharedPref.getInstance(requireContext()).putInt(KEY_FLASH, flashMode)
        }

    /**
     * Turns on or off the grid on the screen
     * */
    private fun toggleGrid() {
        binding.btnGrid.toggleButton(
            flag = hasGrid,
            rotationAngle = 180f,
            firstIcon = R.drawable.ic_grid_off,
            secondIcon = R.drawable.ic_grid_on,
        ) { flag ->
            hasGrid = flag
            SharedPref.getInstance(requireContext()).putBoolean(KEY_GRID, flag)
            binding.groupGridLines.visibility = if (flag) View.VISIBLE else View.GONE
        }
    }

    /**
     * Turns on or off the HDR if available
     * */
    private fun toggleHdr() {
        binding.btnHdr.toggleButton(
            flag = hasHdr,
            rotationAngle = 360f,
            firstIcon = R.drawable.ic_hdr_off,
            secondIcon = R.drawable.ic_hdr_on,
        ) { flag ->
            hasHdr = flag
            SharedPref.getInstance(requireContext()).putBoolean(KEY_HDR, flag)
            startCamera()
        }
    }

    override fun onPermissionGranted() {
        // Each time apps is coming to foreground the need permission check is being processed
        binding.viewFinder.let { vf ->
            vf.post {
                // Setting current display ID
                displayId = vf.display.displayId
                startCamera()

            }
        }
    }

    /**
     * Unbinds all the lifecycles from CameraX, then creates new with new parameters
     * */
    private fun startCamera() {
        // This is the CameraX PreviewView where the camera will be rendered
        val viewFinder = binding.viewFinder

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
            } catch (e: InterruptedException) {
                Toast.makeText(requireContext(), "Error starting camera", Toast.LENGTH_SHORT).show()
                return@addListener
            } catch (e: ExecutionException) {
                Toast.makeText(requireContext(), "Error starting camera", Toast.LENGTH_SHORT).show()
                return@addListener
            }

            // The display information
            val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
            // The ratio for the output image and preview
            val aspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
            // The display rotation
            val rotation = viewFinder.display.rotation

            val localCameraProvider = cameraProvider
                ?: throw IllegalStateException("Camera initialization failed.")

            // The Configuration of camera preview
            preview = Preview.Builder()
                .setTargetAspectRatio(aspectRatio) // set the camera aspect ratio
                .setTargetRotation(rotation) // set the camera rotation
                .build()

            // The Configuration of image capture
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY) // setting to have pictures with highest quality possible (may be slow)
                .setFlashMode(flashMode) // set capture flash
                .setTargetAspectRatio(aspectRatio) // set the capture aspect ratio
                .setTargetRotation(rotation) // set the capture rotation
                .also {
                    // Create a Vendor Extension for HDR
                    val hdrImageCapture = HdrImageCaptureExtender.create(it)

                    // Check if the extension is available on the device
                    if (!hdrImageCapture.isExtensionAvailable(lensFacing)) {
                        // If not, hide the HDR button
                        binding.btnHdr.visibility = View.GONE
                    } else if (hasHdr) {
                        // If yes, turn on if the HDR is turned on by the user
                        hdrImageCapture.enableExtension(lensFacing)
                    }
                }
                .build()

            // The Configuration of image analyzing
            imageAnalyzer = ImageAnalysis.Builder()
                .setTargetAspectRatio(aspectRatio) // set the analyzer aspect ratio
                .setTargetRotation(rotation) // set the analyzer rotation
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) // in our analysis, we care about the latest image
                .build()
                .apply {
                    // Use a worker thread for image analysis to prevent glitches
                    val analyzerThread = HandlerThread("LuminosityAnalysis").apply { start() }
                    setAnalyzer(
                        ThreadExecutor(Handler(analyzerThread.looper)),
                        LuminosityAnalyzer()
                    )
                }

            localCameraProvider.unbindAll() // unbind the use-cases before rebinding them

            try {
                // Bind all use cases to the camera with lifecycle
                localCameraProvider.bindToLifecycle(
                    viewLifecycleOwner, // current lifecycle owner
                    lensFacing, // either front or back facing
                    preview, // camera preview use case
                    imageCapture, // image capture use case
                    imageAnalyzer, // image analyzer use case
                )

                // Attach the viewfinder's surface provider to preview use case
                preview?.setSurfaceProvider(viewFinder.surfaceProvider)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to bind use cases", e)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    /**
     *  Detecting the most suitable aspect ratio for current dimensions
     *
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    @Suppress("NON_EXHAUSTIVE_WHEN")
    private fun takePicture() = lifecycleScope.launch(Dispatchers.Main) {
        captureImage()
    }

    private fun captureImage() {
        try {
            var file:File?=null
            val localImageCapture = imageCapture ?: throw IllegalStateException("Camera initialization failed.")

            // Setup image capture metadata
            val metadata = ImageCapture.Metadata().apply {
                // Mirror image when using the front camera
                isReversedHorizontal = lensFacing == CameraSelector.DEFAULT_FRONT_CAMERA
            }
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            // Options fot the output image file
            val outputOptions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis())
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, outputDirectory)
                }

                val contentResolver = requireContext().contentResolver

                // Create the output uri
                val contentUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                ImageCapture.OutputFileOptions.Builder(contentResolver, contentUri, contentValues)

            } else {

                var newFile: File?
                newFile = File(outputDirectory)
                newFile.mkdirs()
                file = File(newFile, "${System.currentTimeMillis()}.jpg")
                ImageCapture.OutputFileOptions.Builder(file)
            }.setMetadata(metadata).build()

            localImageCapture.takePicture(
                outputOptions, // the options needed for the final image
                requireContext().mainExecutor(), // the executor, on which the task will run
                object : ImageCapture.OnImageSavedCallback { // the callback, about the result of capture process
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                        // This function is called if capture is successfully completed
//                    outputFileResults.savedUri
//                        ?.let { uri ->
//                            setGalleryThumbnail(uri)
//                            Log.d(TAG, "Photo saved in $uri")
//                        }
//                        ?: setLastPictureThumbnail()
//                     openPreview()
                        try {
                            getMedia().firstOrNull()?.let {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                    openPreview(it)
                                }else{
                                    file?.let {
                                        openPreview(Media(it.path.toUri(),false,it.path,it))
                                    }

                                }

                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        // This function is called if there is an errors during capture process
                        val msg = "Photo capture failed: ${exception.message}"
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, msg)
                        exception.printStackTrace()
                    }
                }
            )
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        displayManager.unregisterDisplayListener(displayListener)
    }

    override fun onBackPressed() = when {
        binding.llFlashOptions.visibility == View.VISIBLE -> binding.llFlashOptions.circularClose(binding.btnFlash)
        else -> requireActivity().finish()
    }

    companion object {
        private const val TAG = "Media"

        const val KEY_FLASH = "sPrefFlashCamera"
        const val KEY_GRID = "sPrefGridCamera"
        const val KEY_HDR = "sPrefHDR"

        private const val RATIO_4_3_VALUE = 4.0 / 3.0 // aspect ratio 4x3
        private const val RATIO_16_9_VALUE = 16.0 / 9.0 // aspect ratio 16x9
    }

}