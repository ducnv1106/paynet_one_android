package com.paynetone.counter.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputFilter
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.R
import com.paynetone.counter.databinding.DialogPinCodeBinding
import com.paynetone.counter.model.request.PINAddRequest
import com.paynetone.counter.model.request.RequestGetOtp
import com.paynetone.counter.model.request.RequestOtp
import com.paynetone.counter.model.response.AddPINCodeResponse
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import org.apache.commons.lang3.StringUtils
import java.util.*
import java.util.concurrent.TimeUnit

class PinCodeDialog : BaseDialogFragment<DialogPinCodeBinding>(){

    private lateinit var title:String
    private lateinit var sharedPref :SharedPref
    var IsGetOTP = true
    private lateinit var countDownTimer: CountDownTimer

    override fun loadControlsAndResize(binding: DialogPinCodeBinding) {
        binding.apply {
            initViewPINCode()
            tvTitle.text = title
            edtPinCode.isPasswordHidden = true
            edtPinCodeConfirm.isPasswordHidden = true
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)
        arguments?.apply {
            title = getString(ExtraConst.EXTRA_TITLE,"")
        }
    }

    override fun initView(){
        sharedPref = SharedPref.getInstance(requireContext())
        binding.apply {
            edtMobileNumber.text = sharedPref.employeeModel.mobileNumber
            ivBack.setSingleClick {
                dismiss()
            }
            buttonPasswordToggle.setSingleClick {
                edtPinCode.isPasswordHidden = !edtPinCode.isPasswordHidden
                if (edtPinCode.isPasswordHidden)
                    buttonPasswordToggle.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_show_password))
                else
                    buttonPasswordToggle.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_hide_password))


            }
            buttonPasswordToggleConfirm.setSingleClick {
                edtPinCodeConfirm.isPasswordHidden = !edtPinCodeConfirm.isPasswordHidden
                if (edtPinCodeConfirm.isPasswordHidden)
                    buttonPasswordToggleConfirm.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_show_password))
                else buttonPasswordToggleConfirm.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_hide_password))
            }
            btnSendRequest.setSingleClick {
                if (confirmInput()) requestPinCode()
            }
            rootView.setSingleClick {
                it.hideKeyboard()
            }
            edtPinCode.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) layoutPinCode.background =  ResourcesCompat.getDrawable(resources,R.drawable.bg_pin_code_selected,null)
                else layoutPinCode.background = ResourcesCompat.getDrawable(resources,R.drawable.bg_edt_forgot_password,null)
            }
            edtPinCodeConfirm.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) layoutPinCodeConfirm.background =  ResourcesCompat.getDrawable(resources,R.drawable.bg_pin_code_selected,null)
                    else layoutPinCodeConfirm.background = ResourcesCompat.getDrawable(resources,R.drawable.bg_edt_forgot_password,null)
                }

            btnOtp.setSingleClick {
                if (IsGetOTP) {
                    if (TextUtils.isEmpty(edtMobileNumber.getText())) {
                        Toast.showToast(context, "Bạn chưa nhập số điện thoại")
                        return@setSingleClick
                    }
                    if ((edtMobileNumber.text?.length ?: 0) < 10) {
                        Toast.showToast(context, "Bạn chưa nhập đúng số điện thoại")
                        return@setSingleClick
                    }
                    if (edtMobileNumber.text.toString()[0] != '0') {
                        Toast.showToast(requireContext(), R.string.error_warning_phone)
                        return@setSingleClick
                    }
                    requestOTP()
                }
            }
            btnVerifyOtp.setSingleClick {
                if (TextUtils.isEmpty(edtMobileNumber.getText())) {
                    Toast.showToast(context, "Bạn chưa nhập số điện thoại")
                    return@setSingleClick
                }
                if ((edtMobileNumber.text?.length ?: 0) < 10) {
                    Toast.showToast(context, "Bạn chưa nhập đúng số điện thoại")
                    return@setSingleClick
                }
                if (edtMobileNumber.text.toString()[0] != '0') {
                    Toast.showToast(requireContext(), R.string.error_warning_phone)
                    return@setSingleClick
                }
                if (TextUtils.isEmpty(edtOtp.text)) {
                    Toast.showToast(context, "Bạn chưa nhập OTP")
                    return@setSingleClick
                }
                requestVerifyOTP()
            }
        }
    }

    private fun validatePINCode():Boolean{
        val pinCode = binding.edtPinCode.text.toString()
        if (pinCode.isBlank()){
            Toast.showToast(requireContext(),resources.getString(R.string.str_not_enter_pin_code))
            return false
        }
        return true
    }
    private fun validatePINCodeConfirm():Boolean{
        val pinCode = binding.edtPinCodeConfirm.text.toString()
        if (pinCode.isBlank()){
            Toast.showToast(requireContext(),resources.getString(R.string.str_not_enter_pin_code))
            return false
        }
        return true
    }
    private fun validateNotMatchPinCode() : Boolean{
        val pinCode = binding.edtPinCode.text.toString()
        val pinCodeConfirm = binding.edtPinCodeConfirm.text.toString()
        if (pinCode != pinCodeConfirm){
            Toast.showToast(requireContext(),resources.getString(R.string.str_not_match_pin_code))
            return false
        }
        return true
    }

    private fun confirmInput(): Boolean {
        if (!validatePINCode() || !validatePINCodeConfirm() || !validateNotMatchPinCode()) {
            return false
        }
        return true
    }

    private fun requestPinCode(): Disposable {
        val empId = SharedPref.getInstance(requireContext()).employeeModel.id
        val pinCode = binding.edtPinCode.text.toString()
        val mobileNumber = SharedPref.getInstance(requireContext()).employeeModel.mobileNumber
        showProgressDialog()
        return NetWorkController.requestAddPinCode(PINAddRequest(empId ?: 0,pinCode,"",mobileNumber))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00"){
                    val response = NetWorkController.getGson().fromJson<AddPINCodeResponse>(result.data,object : TypeToken<AddPINCodeResponse>(){}.type)
                    response.pinCode?.let {
                        sharedPref.putString(PrefConst.PREF_IS_EXIST_PIN_CODE,it)
                    }
                    val successDialog =  SuccessPaymentDialog.getInstance(getString(R.string.str_setting_pin_code_success))
                    successDialog.setCallBackListener(object : SuccessPaymentDialog.CallBackListener{
                        override fun onCloseClicked() {
                            this@PinCodeDialog.dismiss()
                        }

                    })
                    successDialog.show(childFragmentManager,"PinCodeDialog")
                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                throwable.printStackTrace()
                hideProgressDialog()
            }
    }
    private fun requestOTP(): Disposable{
        val mobileNumber = SharedPref.getInstance(requireContext()).employeeModel.mobileNumber
        showProgressDialog()
        return NetWorkController.getOTP(RequestGetOtp(mobileNumber=mobileNumber, oTPType = "P"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00"){
                    Toast.showToast(requireContext(), resources.getString(R.string.xac_thuc_otp) + " " + binding.edtMobileNumber.text.toString() + " (kiểm tra cuộc gọi và ZALO)")
                    IsGetOTP = false
                    countDownOTP((3 * 60 * 1000).toLong())
                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                throwable.printStackTrace()
                hideProgressDialog()
            }
    }

    private fun requestVerifyOTP(): Disposable{
        val mobileNumber = SharedPref.getInstance(requireContext()).employeeModel.mobileNumber
        val otpValue = binding.edtOtp.text.toString()
        showProgressDialog()
        return NetWorkController.requestOtp(RequestOtp(mobileNumber=mobileNumber, oTPType = "P", oTPValue = otpValue))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00"){
                    binding.apply {
                        layoutContent.visibility = View.VISIBLE
                        btnSendRequest.visibility = View.VISIBLE
                        layoutOtp.visibility = View.GONE
                        btnVerifyOtp.visibility = View.GONE
                        countDownTimer.onFinish()
                    }
                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                throwable.printStackTrace()
                hideProgressDialog()
            }
    }


    private fun countDownOTP(diff: Long) {
        countDownTimer = object : CountDownTimer(diff, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                var millisUntilFinished = millisUntilFinished
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                binding.btnOtp.text = StringUtils.leftPad(minutes.toString(), 2, '0') + ":" + StringUtils.leftPad(seconds.toString(), 2, '0')
            }

            override fun onFinish() {
                cancel()
                binding.btnOtp.text = "Gửi lại mã OTP"
                IsGetOTP = true
            }
        }
        countDownTimer.start()
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogPinCodeBinding.inflate(inflater,container,false)

    private fun initViewPINCode(){
        binding.apply {
            val filters = arrayOfNulls<InputFilter>(2)
            filters[0] = InputFilter.LengthFilter(4)
            filters[1] = InputFilterCharacterNumber()
            edtPinCode.filters = filters
            edtPinCodeConfirm.filters = filters
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            countDownTimer.onFinish()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(title:String) = PinCodeDialog().apply {
            arguments = Bundle().apply {
                putString(ExtraConst.EXTRA_TITLE,title)
            }
        }
    }


}