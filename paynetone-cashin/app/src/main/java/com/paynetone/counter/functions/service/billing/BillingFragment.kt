package com.paynetone.counter.functions.service.billing

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.R
import com.paynetone.counter.databinding.BillingFragmentBinding
import com.paynetone.counter.dialog.ErrorDialog
import com.paynetone.counter.dialog.ListBankQRDialog
import com.paynetone.counter.dialog.SuccessPaymentDialog
import com.paynetone.counter.enumClass.StateNotify
import com.paynetone.counter.enumClass.StateSaveImage
import com.paynetone.counter.model.PaynetModel
import com.paynetone.counter.model.request.BaseRequest
import com.paynetone.counter.model.request.GetProviderResponse
import com.paynetone.counter.model.response.ResponseMerchantBalance
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.observer.DisplayElement
import com.paynetone.counter.observer.Observer
import com.paynetone.counter.observer.StateNotifyData
import com.paynetone.counter.observer.StateSaveImageData
import com.paynetone.counter.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import java.io.File

class BillingFragment : DialogFragment(), DisplayElement<Any>, Observer<Any> {
    private lateinit var url: String
    private lateinit var title:String
    private var amount: Long = 0L
    private var isBooking:Boolean = false
    private var isShowErr:Boolean = true

    private var urlSession :String? = null
    private lateinit var binding: BillingFragmentBinding
    private var hotline :String? = null
    private var errorDialog: ErrorDialog? = null
    private var paynetModel: PaynetModel? = null
    private var uriShareImage : Uri? = null
    private var mode = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.billing_fragment, container, false)
        binding.lifecycleOwner = this
        initView()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setStyle(STYLE_NO_FRAME, R.style.FullScreenDialogListBankQR)
        arguments?.apply {
            url = getString(ExtraConst.EXTRA_URL_BILLING,"")
            title = getString(ExtraConst.EXTRA_TITLE,"")
            amount = getLong(ExtraConst.EXTRA_AMOUNT,0L)
            isBooking = getBoolean(ExtraConst.EXTRA_IS_BOOKING,false)
            isShowErr = getBoolean(ExtraConst.EXTRA_SHOW_ERROR,true)
        }

    }

    private fun initView() {
        initWebView()
        this@BillingFragment.urlSession = url
        paynetModel = SharedPref.getInstance(requireContext()).paynet
        binding.apply {
            mode = SharedPref.getInstance(requireContext()).getString(Constants.KEY_ANDROID_PAYMENT_MODE, "")
            val rect = Rect()
            refreshLayout.getDrawingRect(rect)
            refreshLayout.setProgressViewOffset(false, 0, 100)

            title?.let {
                tvTitle.text = it
            }
            tvAmount.text = NumberUtils.formatPriceNumber(amount ?: 0L) + " VNĐ"


            if (mode == Constants.ANDROID_PAYMENT_MODE_HIDE){
                viewLine1.visibility = View.GONE
                viewLine2.visibility = View.GONE
                refreshLayout.layoutParams.height = 120
                layoutContentHeader.visibility = View.GONE
            }
            tvCodeMerchant.text = paynetModel?.name ?: ""

            ivBack.setSingleClick {
                if (isBooking){
                    dismiss()
                }else{
                    if (webview.canGoBack()) {
                        webview.goBack()
                    } else {
                        dismiss()
                    }
                }

                urlSession?.let {
                    if (urlSession == url){
                        dismiss()
                    }
                }
            }
            buttonRefresh.setSingleClick {
                requestBalance()
            }

        }
        onRefreshListener()


    }


    private fun initWebView() {
        binding.webview.apply {
            settings.javaScriptEnabled = true
            setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                    Log.e("TAG", "shouldOverrideUrlLoading: $url", )
                    if (url?.startsWith("tel:")==true) return true
                    view.loadUrl(url!!)
                    this@BillingFragment.urlSession = url
                    return true
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressLoadingWebview.visibility = View.GONE
                }

                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    showErrorDialog(resources.getString(R.string.str_unable_to_load_data))
                    super.onReceivedError(view, request, error)
                }

                override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
                    resend?.sendToTarget()
                }

            }
            addJavascriptInterface(CaptureClickJavascriptInterface(), "Android")
            loadUrl(this@BillingFragment.url)
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.domStorageEnabled = true

        }
    }

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            requestBalance()
            binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)
            binding.refreshLayout.isRefreshing=false
//            Handler(Looper.getMainLooper()).postDelayed({
//                binding.refreshLayout.isRefreshing=false
//
//            }, 2000L)

        }
    }

    private fun requestBalance(): Disposable {
        showProgressDialog()
        val payNetId = SharedPref.getInstance(requireContext()).employeeModel.paynetID ?: 0
        val request = BaseRequest()
        request.paynetID = payNetId
        return NetWorkController.getBalance(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00"){
                    val responses = NetWorkController.getGson().fromJson<ResponseMerchantBalance>(result.data, object : TypeToken<ResponseMerchantBalance>() {}.type)
                    responses.getMerchantBalances()?.let {
                        for (item in it){
                            if (item.accountType == "S"){
                                amount = item.balance
                                binding.tvAmount.text = NumberUtils.formatPriceNumber(amount ?: 0L) + " VNĐ"
                            }
                        }
                    }

                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                hideProgressDialog()
                throwable.printStackTrace()
            }
    }

    inner class CaptureClickJavascriptInterface {

        @JavascriptInterface
        fun handleShareScreen() {
            if (allPermissionsGranted()) {
                onPermissionShareImageGranted()
            } else {
                permissionShareImageRequest.launch(permissions.toTypedArray())
            }

        }
        @JavascriptInterface
        fun saveImageToStorage() {
            if (allPermissionsGranted()) {
                onPermissionSaveImageToStorageGranted()
            } else {
                permissionSaveImageToStorageRequest.launch(permissions.toTypedArray())
            }

        }

        @JavascriptInterface
        fun handlerCallPhoneClick(phone: String) {
            this@BillingFragment.hotline = phone
            if (permissionCallPhoneGranted()) {
                onPermissionCalLPhoneGranted()
            } else {
                permissionCallPhoneRequest.launch(permissionCallPhone.toTypedArray())

            }

        }



    }

    // The permissions we need for the app to work properly
    private val permissions = mutableListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private val permissionCallPhone = mutableListOf(Manifest.permission.CALL_PHONE)

    private fun allPermissionsGranted() = permissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    private fun permissionCallPhoneGranted() = permissionCallPhone.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionShareImageRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                onPermissionShareImageGranted()
            } else {
                Toast.showToast(activity,resources.getString(R.string.str_no_memory_assess))
            }
        }
    private val permissionSaveImageToStorageRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                onPermissionSaveImageToStorageGranted()
            } else {
                Toast.showToast(activity,resources.getString(R.string.str_no_memory_assess))
            }
        }
    private val permissionCallPhoneRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.all { it.value }) {
            onPermissionCalLPhoneGranted()
        } else {
            Toast.showToast(activity,resources.getString(R.string.str_no_call_phone_assess))
        }
    }

    // handler after the permission check
    private fun onPermissionShareImageGranted() {
       getScreenShot(binding.webview)?.let {
          onShareImageWithApplication(it)
       }

    }

    // handler after the permission check
    private fun onPermissionSaveImageToStorageGranted() {
        getScreenShot(binding.webview)?.let {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    showProgressDialog()
                    saveMediaToStorage(it,requireContext())
                } catch (e: IOException) {
                    hideProgressDialog()
                    showErrorDialog(resources.getString(R.string.str_image_download_failed))
                }

            }
        }

    }

    // handler after the permission call phone check
    private fun onPermissionCalLPhoneGranted() {
        hotline?.let {
            val intent =
                Intent(Intent.ACTION_CALL, Uri.parse("tel:$it"))
            startActivity(intent)
        }

    }

    private fun showErrorDialog(message:String) {
        binding.progressLoadingWebview.visibility = View.GONE
        if (errorDialog==null){
            errorDialog = ErrorDialog.getInstance(message)
        }
        if (isShowErr) childFragmentManager.beginTransaction().add(errorDialog!!,"errorDialog").commitAllowingStateLoss()
    }

   private fun onShareImageWithApplication(bitmap: Bitmap){
        try {
            val path = MediaStore.Images.Media.insertImage(activity?.contentResolver, bitmap, "Image Description", null)
            val uri = Uri.parse(path)
            this.uriShareImage = uri
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, uri)
//            startActivityForResult(Intent.createChooser(intent, "Share Image"),111)
            startForResult.launch(Intent.createChooser(intent, "Share Image"))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _: ActivityResult ->
            try {
                var fDelete: File? = null
                if (Build.VERSION.SDK_INT >= 29) {
                    uriShareImage?.let {
                        fDelete = File(it.path)
                    }

                } else {
                    uriShareImage?.let {
                        fDelete = File(getRealPathFromURI(it, requireContext()))
                    }
                }
                fDelete?.delete(requireContext())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    private fun showProgressDialog() { DialogUtils.showProgressDialog(activity) }

    private fun hideProgressDialog() =   DialogUtils.dismissProgressDialog()

    private fun getRealPathFromURI(uri: Uri, context: Context): String {
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

    override fun display(data: Any) {
        if (data is StateSaveImageData){
            data.also {  stateSaveImage ->
                when(stateSaveImage.state){
                    StateSaveImage.SUCCESS ->{
                        hideProgressDialog()
                        CoroutineScope(Dispatchers.Main).launch {
                            val successDialog =  SuccessPaymentDialog.getInstance(getString(R.string.str_download_image_success))
                            successDialog.show(childFragmentManager,"PinCodeDialog")
                        }


                    }
                    else->{}
                }
            }
        }

    }

    override fun update(data: Any) {
        display(data)
    }

    override fun onStart() {
        super.onStart()
        StateSaveImageData.registerObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        StateSaveImageData.removeObserver(this)
        StateNotifyData.setMeasurements(StateNotify.MAIN_UPDATE)
    }

    companion object{
        @JvmStatic
        fun getInstance(url:String,  title:String?,
                         amount:Long? = null,  isBooking: Boolean = false,  isShowErr: Boolean = true) = BillingFragment().apply {
            arguments = Bundle().apply {
                putString(ExtraConst.EXTRA_URL_BILLING,url)
                putString(ExtraConst.EXTRA_TITLE,title)
                putLong(ExtraConst.EXTRA_AMOUNT,amount ?: 0L)
                putBoolean(ExtraConst.EXTRA_IS_BOOKING,isBooking)
                putBoolean(ExtraConst.EXTRA_SHOW_ERROR,isShowErr)
            }
        }
    }


}