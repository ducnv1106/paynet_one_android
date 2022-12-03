package com.paynetone.counter.dialog

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import brite.outdoor.utils.compressor.resolution
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.paynetone.counter.R
import com.paynetone.counter.adapter.ImagePickAdapterAvatar
import com.paynetone.counter.app.PaynetOneApplication
import com.paynetone.counter.databinding.DialogAddImageBinding
import com.paynetone.counter.functions.media.CameraActivity
import com.paynetone.counter.model.ImagePicker
import com.paynetone.counter.model.Media
import com.paynetone.counter.utils.*
import com.paynetone.counter.utils.compressor.Compressor
import com.paynetone.counter.utils.compressor.format
import com.paynetone.counter.utils.compressor.quality
import com.paynetone.counter.utils.compressor.size
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*

class AddImageDialogFragment(val mContext: Context, val callBack:CallBackListener) : DialogFragment() {

    private lateinit var binding: DialogAddImageBinding
    private lateinit var flow: Flow<ImagePicker>
    private val jobInsertImagePicker = CoroutineScope(Dispatchers.Main)
    private lateinit var adapter:ImagePickAdapterAvatar
    private  var imagePicker: ImagePicker? = null

    interface CallBackListener{
        fun onSelectedItem(imagePicker: ImagePicker,body: MultipartBody.Part?=null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddImageBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)
    }


    private fun initView() {
        binding.apply {
            ivBack.setSingleClick {
                dismiss()
            }
            tvOver.setSingleClick {
                if (imagePicker == null){
                    toast("Vui lòng chọn ảnh!",requireContext())
                    return@setSingleClick
                }
                imagePicker?.let {
                    callBack.onSelectedItem(it,prepareFilePart("image",Uri.parse(it.uri)))
                    dismiss()
                }
            }

        }
        initAdapter()
        if (allPermissionsGranted()) {
            onPermissionGranted()
        } else {
            permissionRequest.launch(permissions.toTypedArray())
        }


    }

    // The permissions we need for the app to work properly
    private val permissions = mutableListOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private fun allPermissionsGranted() = permissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                onPermissionGranted()
            } else {
                toast(resources.getString(R.string.str_no_memory_assess),requireContext())
            }
        }

    // handler after the permission check
    private fun onPermissionGranted() {
        getImagePicker()
        jobInsertImagePicker.launch {
            flow.collect {
                adapter.insertImagePicker(it)
            }
        }
    }

    private fun getImagePicker() {
        flow= flow {

            //create item camera
            emit(ImagePicker(isCamera = true))

            val uriExternal = MediaStore.Files.getContentUri("external")

            //read file local image
            val collectionImage = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projectionImage = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
            )
            val sortOder = "${MediaStore.Images.ImageColumns.DATE_MODIFIED} DESC"
            val cursorImage: Cursor? =
                activity?.contentResolver?.query(
                    collectionImage,
                    projectionImage,
                    null,
                    null,
                    sortOder
                )
            var columnIndexIDImage: String
            try {
                cursorImage?.let {
                    cursorImage.moveToFirst()
                    do {
                        val name =
                            cursorImage.getString(cursorImage.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                        columnIndexIDImage =
                            cursorImage.getString(cursorImage.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                        val uriImage =
                            Uri.withAppendedPath(uriExternal, "" + columnIndexIDImage)
                        val imgPicker = ImagePicker(uri = uriImage.toString())
                        emit(imgPicker)
                    } while (cursorImage.moveToNext())
                    cursorImage.close()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }
    private fun initAdapter(){
        binding.apply {
            with(PaynetOneApplication.getInstance()){
                adapter = ImagePickAdapterAvatar(
                    requireContext(),
                    getSizeWithScale(111.0),
                    getSizeWithScale(16.0),
                    getSizeWithScale(6.0),
                    itemClickListener = { imagePicker -> onClickItem(imagePicker) },
                    getSizeWithScaleFloat(10.0)
                )
            }
            rvImagePicker.apply {
                adapter = this@AddImageDialogFragment.adapter
                addItemDecoration(MarginDecoration(10,4))
            }


        }
    }

    private fun onClickItem(imagePick:ImagePicker?=null) {
        imagePick?.let {
            if (imagePick.isCamera){

//                openCamera()
                startForResult.launch(Intent(requireContext(),CameraActivity::class.java))
            }else{
                this.imagePicker = it
            }
        }
    }

    private var startForResult =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        try {
            if (result.resultCode == RESULT_CODE) {
                result.data?.let {
                    val media = it.getParcelableExtra<Media>(ExtraConst.EXTRA_MEDIA)
                    media?.let {
                        val itemPicker = ImagePicker(uri = it.uri.toString(), path = it.path)
                        callBack.onSelectedItem(itemPicker,prepareFilePart("image",Uri.parse(itemPicker.uri),it.file))
                        dismiss()
                    }


                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openCamera(){
        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale(DialogHelper::showRationaleDialog)
                .callback( object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: MutableList<String>?) {
                        MediaUtils.captureImage(this@AddImageDialogFragment)
                    }

                    override fun onDenied(
                        permissionsDeniedForever: MutableList<String>?,
                        permissionsDenied: MutableList<String>?
                    ) {
                        permissionsDeniedForever?.let {
                            if (it.isNotEmpty()) {
                                DialogHelper.showOpenAppSettingDialog()
                            }
                        }

                    }
                })
                .request()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                var path = data?.data?.path
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    path = path?.replace("///","//")
                    callBack.onSelectedItem(ImagePicker(uri = path ?:"", isCamera = true))

                }else{
                    callBack.onSelectedItem(ImagePicker(uri = path ?:"", isCamera = true))
                }

                this.dismiss()
            }
        }
    }



    private fun prepareFilePart(partName: String, fileUri: Uri,file: File?=null): MultipartBody.Part? {
        try {
            var file  = file ?: File(getRealPathFromURI(fileUri))

            if (file.getSizeInKB() > (3 * 1024)) { // check file photo and > 3mb
                runBlocking(Dispatchers.IO) {
                    file = Compressor.compress(requireContext(), file) {
                        resolution(1280, 720)
                        quality(80)
                        format(Bitmap.CompressFormat.JPEG)
                        size(3_145_728)
                    }

                }
            }
               val requestFile: RequestBody =
                   RequestBody.create(activity?.contentResolver?.getType(fileUri)?.toMediaTypeOrNull(), file)
               return MultipartBody.Part.createFormData(
                   partName, file.name.replace(" ".toRegex(), "%20"), requestFile
               )
        } catch (e: Exception) {
        }
        return null
    }
    private fun getRealPathFromURI(uri: Uri): String {
        try {
            var path = ""
            val proj = arrayOf(MediaStore.MediaColumns.DATA)
            val cursor: Cursor? = activity?.contentResolver?.query(uri, proj, null, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val columnIndex: Int =
                        cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                    path = cursor.getString(columnIndex)
                }
            }
            cursor?.close()
            return path
        } catch (e: Exception) {
        }
        return ""
    }






    override fun onDestroy() {
        super.onDestroy()
        try {
            if (jobInsertImagePicker.isActive) jobInsertImagePicker.cancel() // stop coroutine
            flow.cancellable()  // stop flow
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object{
        const val RESULT_CODE = 101

    }


}