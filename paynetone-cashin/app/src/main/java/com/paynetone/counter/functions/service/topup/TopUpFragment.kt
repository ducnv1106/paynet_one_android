package com.paynetone.counter.functions.service.topup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.core.base.viper.ViewFragment
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.R
import com.paynetone.counter.databinding.TopUpFragmentBinding
import com.paynetone.counter.dialog.ErrorDialog
import com.paynetone.counter.dialog.PhoneContactDialog
import com.paynetone.counter.enumClass.StateNotify
import com.paynetone.counter.model.PaynetModel
import com.paynetone.counter.model.request.BaseRequest
import com.paynetone.counter.model.response.ResponseMerchantBalance
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.observer.StateNotifyData
import com.paynetone.counter.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class TopUpFragment : ViewFragment<TopUpContract.Presenter>(), TopUpContract.View {

    private lateinit var binding: TopUpFragmentBinding
    private var hotline :String? = null
    private var url :String? = null
    private var paynetModel: PaynetModel? = null
    private var mode = ""
    private var amount : Long = 0

    companion object {
        val instance: TopUpFragment
            get() = TopUpFragment()
    }

    override fun getLayoutId(): Int = R.layout.top_up_fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, container, false)
        binding.lifecycleOwner = this
        initView()
        return binding.root
    }

    private fun initView() {
        initWebView()
        amount = mPresenter.amountHanMuc()
        this.url = mPresenter.urlTopUpAddress()
        binding.apply {
            mode = SharedPref.getInstance(requireContext()).getString(Constants.KEY_ANDROID_PAYMENT_MODE, "")
            val rect = Rect()
            refreshLayout.getDrawingRect(rect)
            refreshLayout.setProgressViewOffset(false, 0, 65)
            if (mode == Constants.ANDROID_PAYMENT_MODE_HIDE){
                refreshLayout.visibility = View.GONE
                viewLine1.visibility = View.GONE
                viewLine2.visibility = View.GONE
            }
            tvAmount.text = NumberUtils.formatPriceNumber(amount ?: 0L) + " VNĐ"
            tvCodeMerchant.text = SharedPref.getInstance(requireContext()).paynet?.name ?: ""

            ivBack.setSingleClick {
                if (webview.canGoBack()) {
                    webview.goBack()
                } else {
                    activity?.finish()
                }

                url?.let {
                    if (url == mPresenter.urlTopUpAddress()){
                        activity?.finish()
                    }
                }

            }

            buttonRefresh.setSingleClick {
                requestBalance()
            }

        }
        onRefreshListener()

    }

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            requestBalance()
            binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.refreshLayout.isRefreshing=false
            }, 2000L)

        }
    }

    private fun requestBalance(): Disposable {
        showProgress()
        val payNetId = SharedPref.getInstance(requireContext()).employeeModel.paynetID ?: 0
        val request = BaseRequest()
        request.paynetID = payNetId
        return NetWorkController.getBalance(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgress()
                if (result.errorCode == "00"){
                    val responses = NetWorkController.getGson().fromJson<ResponseMerchantBalance>(result.data, object : TypeToken<ResponseMerchantBalance>() {}.type)
                    responses.getMerchantBalances()?.let {
                        for (item in it){
                            if (item.accountType == "S"){
                                amount= item.balance
                                binding.tvAmount.text = NumberUtils.formatPriceNumber(amount) + " VNĐ"
                            }
                        }
                    }

                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                hideProgress()
                throwable.printStackTrace()
            }
    }

    private fun initWebView() {
        binding.webview.apply {
            settings.javaScriptEnabled = true
            setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorWebView))
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                    view.loadUrl(url!!)
                    this@TopUpFragment.url = url
                    return true
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressLoadingWebview.visibility = View.GONE
                }

                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    showErrorDialog()
                    super.onReceivedError(view, request, error)
                }

                override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
                    resend?.sendToTarget()
                }

            }
            addJavascriptInterface(CaptureClickJavascriptInterface(), "Android")
            if (mPresenter != null) loadUrl(mPresenter.urlTopUpAddress())
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.domStorageEnabled = true
//            settings.databaseEnabled = true
        }
    }


    inner class CaptureClickJavascriptInterface {
        @JavascriptInterface
        fun handleButtonClick() {
            PhoneContactDialog(requireContext()) {
                binding.webview.loadUrl("javascript:passDataToWebPageView('${it.name}','${it.phone}')")
            }.show(childFragmentManager, "PhoneRechareCardFragment")
        }

        @JavascriptInterface
        fun handlerHotlineClick(phoneNumber: String) {
            this@TopUpFragment.hotline = phoneNumber
            if (allPermissionsGranted()) {
                onPermissionGranted()
            } else {
                permissionRequest.launch(permissions.toTypedArray())
            }

        }
        @JavascriptInterface
        fun onBackPressedWebView(){
            activity?.finish()
        }

    }

    // The permissions we need for the app to work properly
    private val permissions = mutableListOf(Manifest.permission.CALL_PHONE)

    private fun allPermissionsGranted() = permissions.all {
        ContextCompat.checkSelfPermission(
            requireContext(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                onPermissionGranted()
            } else {
                Toast.showToast(activity,resources.getString(R.string.str_no_call_phone_assess))
            }
        }

    // handler after the permission check
    private fun onPermissionGranted() {
        hotline?.let {
            val intent =
                Intent(Intent.ACTION_CALL, Uri.parse("tel:$it"))
            startActivity(intent)
        }

    }
    private fun showErrorDialog() {
        binding.progressLoadingWebview.visibility = View.GONE
        ErrorDialog.getInstance("Không tải được dữ liệu!").show(childFragmentManager, "QRFragment")
    }

    override fun onDestroy() {
        StateNotifyData.setMeasurements(StateNotify.MAIN_UPDATE)
        super.onDestroy()
    }
}