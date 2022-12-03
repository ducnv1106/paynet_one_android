package com.paynetone.counter.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.paynetone.counter.R
import com.paynetone.counter.enumClass.StateSaveImage
import com.paynetone.counter.observer.StateSaveImageData
import kotlinx.android.synthetic.main.layout_toast.view.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.concurrent.Executor

fun View.setSingleClick(execution: (View) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var lastClickTime: Long = 0
        override fun onClick(p0: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < Constants.THRESHOLD_CLICK_TIME) {
                return
            }
            lastClickTime = SystemClock.elapsedRealtime()
            execution.invoke(this@setSingleClick)
        }
    })
}
fun View?.hideKeyboard() {
    try {
        val manager =
            this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(this.windowToken, 0)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}
fun AppCompatEditText.passwordToggle(context: Context,imageView: AppCompatImageView){
    if (this.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
        this.transformationMethod = HideReturnsTransformationMethod.getInstance()
        imageView.setImageDrawable(
            ContextCompat.getDrawable(context,
            R.drawable.ic_hide_password))
    } else {
        this.transformationMethod = PasswordTransformationMethod.getInstance()
        imageView.setImageDrawable(
            ContextCompat.getDrawable(context,
            R.drawable.ic_show_password))
    }
    this.setSelection(this.length())
}
fun AppCompatTextView.handlerCopyText(text :String,context: Context){
    val myClipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val myClip: ClipData = ClipData.newPlainText("note_copy", text)
    myClipboard.setPrimaryClip(myClip)
    Toast.showToast(context,"Sao chép thành công!")
}

fun ImageView.loadImageWithGlide(url:String){
    Glide.with(this)
        .load(Utils.getUrlImage(url))
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}
fun ImageView.loadImageWithGlideResource(drawable:Drawable?){
    Glide.with(this)
        .load(drawable)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}
fun ImageView.loadImageWithGlideUri(uri:Uri){
    Glide.with(this)
        .load(uri)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun AppCompatEditText.disableCopyPaste() {
    isLongClickable = false
    setTextIsSelectable(false)
    customSelectionActionModeCallback = object : android.view.ActionMode.Callback {
        override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?) = false

        override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?) = false

        override fun onActionItemClicked(mode: android.view.ActionMode?, item: MenuItem?)= false

        override fun onDestroyActionMode(mode: android.view.ActionMode?) {}
    }
    //disable action mode when edittext gain focus at first
    customInsertionActionModeCallback = object : android.view.ActionMode.Callback {
        override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?)= false

        override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?)= false

        override fun onActionItemClicked(mode: android.view.ActionMode?, item: MenuItem?)= false

        override fun onDestroyActionMode(mode: android.view.ActionMode?) {}
    }
}

suspend fun saveMediaToStorage(bitmap: Bitmap, context: Context) =  withContext(Dispatchers.IO) {
    try {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"
        var file : File? = null

        val outputDirectory =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) "${Environment.DIRECTORY_DCIM}/PaynetOne/"
        else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/PaynetOne/"

        //Output stream
        var fos: OutputStream? = null

        var newFile: File?
        newFile = File(outputDirectory)
        newFile.mkdirs()
        file = File(newFile, filename)

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            context.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, outputDirectory)
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            fos = FileOutputStream(file)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            scanFile(file?.absolutePath ?: "",context)
            StateSaveImageData.setMeasurements(StateSaveImage.SUCCESS)
        }
    }catch (e:Exception){
        e.printStackTrace()
    }

}

fun scanFile(path: String,context: Context) {
    MediaScannerConnection.scanFile(context, arrayOf(path), null) { path, uri -> }
}

fun getScreenShot(view: View): Bitmap? {
//    val screenView = view.rootView
    view.isDrawingCacheEnabled = true
    val bitmap = Bitmap.createBitmap(view.drawingCache)
    view.isDrawingCacheEnabled = false
    return bitmap
}

fun File.delete(context: Context) {
    try {
        var selectionArgs = arrayOf(this.absolutePath)
        val contentResolver = context.getContentResolver()
        var where: String? = null
        var filesUri: Uri? = null
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            filesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            where = MediaStore.Images.Media._ID + "=?"
            selectionArgs = arrayOf(this.name)
        } else {
            where = MediaStore.MediaColumns.DATA + "=?"
            filesUri = MediaStore.Files.getContentUri("external")
        }
        contentResolver.delete(filesUri!!, where, selectionArgs)

    }catch (e:Exception){
        e.printStackTrace()
    }


}

fun getRealPathFromURI(uri: Uri,context: Context): String {
    try {
        var path = ""
        val proj = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor? = context.contentResolver?.query(uri, proj, null, null, null)
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
        e.printStackTrace()
    }
    return ""
}

fun ImageButton.toggleButton(
    flag: Boolean, rotationAngle: Float, @DrawableRes firstIcon: Int, @DrawableRes secondIcon: Int,
    action: (Boolean) -> Unit
) {
    if (flag) {
        if (rotationY == 0f) rotationY = rotationAngle
        animate().rotationY(0f).apply {
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    action(!flag)
                }
            })
        }.duration = 200
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            setImageResource(firstIcon)
        }
    } else {
        if (rotationY == rotationAngle) rotationY = 0f
        animate().rotationY(rotationAngle).apply {
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    action(!flag)
                }
            })
        }.duration = 200
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            setImageResource(secondIcon)
        }
    }
}

fun ViewGroup.circularReveal(button: ImageButton) {
    this.visibility = View.VISIBLE
    ViewAnimationUtils.createCircularReveal(
        this,
        button.x.toInt() + button.width / 2,
        button.y.toInt() + button.height / 2,
        0f,
        if (button.context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) this.width.toFloat() else this.height.toFloat()
    ).apply {
        duration = 500
    }.start()
}

fun ViewGroup.circularClose(button: ImageButton, action: () -> Unit = {}) {
    ViewAnimationUtils.createCircularReveal(
        this,
        button.x.toInt() + button.width / 2,
        button.y.toInt() + button.height / 2,
        if (button.context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) this.width.toFloat() else this.height.toFloat(),
        0f
    ).apply {
        duration = 500
        doOnStart { action() }
        doOnEnd { this@circularClose.visibility = View.GONE }
    }.start()
}

fun View.onWindowInsets(action: (View, WindowInsetsCompat) -> Unit) {
    ViewCompat.requestApplyInsets(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        action(v, insets)
        insets
    }
}

fun Window.fitSystemWindows() {
    WindowCompat.setDecorFitsSystemWindows(this, false)
}

fun Context.mainExecutor(): Executor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    mainExecutor
} else {
    MainExecutor()
}

val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

var View.topMargin: Int
    get() = (this.layoutParams as ViewGroup.MarginLayoutParams).topMargin
    set(value) {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = value
        this.layoutParams = params
    }

var View.bottomMargin: Int
    get() = (this.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
    set(value) {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = value
        this.layoutParams = params
    }

var View.endMargin: Int
    get() = (this.layoutParams as ViewGroup.MarginLayoutParams).marginEnd
    set(value) {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.marginEnd = value
        this.layoutParams = params
    }

fun File.getSizeInKB(): Int {
    return (this.length() / (1024)).toInt()
}

fun toast(message: String,context: Context) {
    val parent: ViewGroup? = null
    val toast = android.widget.Toast.makeText(context, "", android.widget.Toast.LENGTH_LONG)
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val toastView = inflater.inflate(R.layout.layout_toast, parent)
    toastView.messageToast.text = message
    toast.view = toastView
    toast.show()
}

fun View.resizeLayout(w: Int, h: Int) {
    try {
        layoutParams.apply {
            width = w
            height = h
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.resizeWidth(w: Int) {
    try {
        layoutParams.apply {
            width = w
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.resizeWidthWithHeightMatchParent(w: Int) {
    try {
        layoutParams.apply {
            width = w
            height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.resizeHeight(h: Int) {
    try {
        layoutParams.apply {
            height = h
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.resizeLayout(size: ViewSize) {
    try {
        layoutParams.apply {
            width = size.width.toInt()
            height = size.height.toInt()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}