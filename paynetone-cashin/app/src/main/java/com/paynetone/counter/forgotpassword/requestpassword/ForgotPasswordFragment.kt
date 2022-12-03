package com.paynetone.counter.forgotpassword.requestpassword

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import com.core.base.viper.ViewFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.FragmentForgotPasswordBinding
import com.paynetone.counter.dialog.ForgotPassWordSuccessDialog
import com.paynetone.counter.model.request.ChangePassByOTPRequest
import com.paynetone.counter.model.request.RequestOtp
import com.paynetone.counter.model.request.UpdatePasswordRequest
import com.paynetone.counter.utils.*
import com.paynetone.counter.utils.Constants.TYPE_GET_OTP_FORGOT_PASSWORD

class ForgotPasswordFragment : ViewFragment<ForgotPasswordContract.Presenter>(), ForgotPasswordContract.View {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var countDownTimer: CountDownTimer
    private val timer by lazy { (3 * 60 * 1000).toLong() }
    private var isRunning = false

    companion object {
        val instance: ForgotPasswordFragment
            get() = ForgotPasswordFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_forgot_password

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),layoutId,container,false)
        binding.lifecycleOwner=this
        initView()
        return binding.root
    }

    private fun initView(){
        binding.apply {
            val text = "Nhập mã xác thực <br> Quý khách vui lòng nhập mã OTP đã được  gửi về Zalo hoặc tin nhắn <br> vào số điện thoại <font color='#027FFE'>${hideTextPhone(mPresenter.phoneNumber())}</font>."
            tvTitleOtp.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

        }
        countDownOTP(timer)
        onListener()
    }

    private fun onListener(){
        binding.apply {
            btnSendRequest.setSingleClick {
                if (confirmInput()){
                    val password = edtPassword.text.toString()
                    mPresenter.updatePassword(ChangePassByOTPRequest(mPresenter.phoneNumber(), opTPValue = otpView.otp.toString(), passwordNew = password))
                }
            }
            rootView.setSingleClick {
                it.hideKeyboard()
            }

            buttonPasswordToggle.setSingleClick {
                edtPassword.passwordToggle(requireContext(),buttonPasswordToggle)
            }
            buttonConfirmPasswordToggle.setSingleClick {
                edtConfirmPassword.passwordToggle(requireContext(),buttonConfirmPasswordToggle)
            }
            ivBack.setSingleClick {
                activity?.finish()
                countDownTimer.onFinish()
            }
            tvTimeRequest.setSingleClick {
                if (!isRunning) mPresenter.getOTP(mPresenter.phoneNumber(),TYPE_GET_OTP_FORGOT_PASSWORD)

            }
            btnRequestOtp.setSingleClick {
                if (validateOTP()){
                    mPresenter.requestOtp(RequestOtp(mobileNumber = mPresenter.phoneNumber(), oTPValue = otpView.otp.toString()))
                }
            }


        }
    }
    override fun showError(message: String?) {
    }

    override fun showSuccessOTP() {
        countDownOTP(timer)
    }

    override fun updatePasswordSuccess() {
        countDownTimer.onFinish()
        ForgotPassWordSuccessDialog().show(childFragmentManager,"ForgotPasswordFragment")
//        activity?.finish()

    }

    override fun requestOtpSuccess() {
        try {
            binding.apply {
                countDownTimer.onFinish()
                layoutRequestPassword.visibility = View.VISIBLE
                layoutConfirmOtp.visibility = View.GONE
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun countDownOTP(timer: Long) {
        try {
            countDownTimer = object : CountDownTimer(timer, 1000) {
                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    isRunning = true
                    val seconds = millisUntilFinished/1000
                    if (seconds==0L){
                        val textTime = "<font color='#03A9F4'>\nGửi lại mã</font>"
                        binding.tvTimeRequest.text = HtmlCompat.fromHtml(textTime, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    }else{
                        val textTime = "<font color='#03A9F4'>\nGửi lại mã (${seconds})</font>"
                        binding.tvTimeRequest.text = HtmlCompat.fromHtml(textTime, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    }

                }

                override fun onFinish() {
                    cancel()
                    isRunning = false
                }
            }
            countDownTimer.start()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    private fun validatePassword(): Boolean {
        val password = binding.edtPassword.text.toString()
        if (password.isBlank()) {
            Toast.showToast(requireContext(),resources.getString(R.string.str_not_enter_password))
            return false
        }
        if (password.length<6 || password.length>50) {
            Toast.showToast(requireContext(),resources.getString(R.string.str_message_field_password_invalid))
            return false
        }
        if (!Utils.passwordValidation(password)){
            Toast.showToast(requireContext(),resources.getString(R.string.str_message_field_password_not_strong))
            return false
        }
        return true

    }
    private fun validateConfirmPassword(): Boolean {
        val confirmPassword = binding.edtConfirmPassword.text.toString()
        if (confirmPassword.isBlank()) {
            Toast.showToast(requireContext(),resources.getString(R.string.str_not_enter_confirm_password))
            return false
        }
        if (confirmPassword.length<6 || confirmPassword.length>50){
            Toast.showToast(requireContext(),resources.getString(R.string.str_message_field_password_invalid))
            return false
        }
        if (!Utils.passwordValidation(confirmPassword)){
            Toast.showToast(requireContext(),resources.getString(R.string.str_message_field_password_not_strong))
            return false
        }
        return true
    }
    private fun validateNotMatchPassword() : Boolean{
        val password = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()
        if (password != confirmPassword){
            Toast.showToast(requireContext(),resources.getString(R.string.str_not_match_password))
            return false
        }
        return true
    }
    private fun validateOTP():Boolean{
        val otp = binding.otpView.otp.toString().trim()
        if (otp.isBlank()){
            Toast.showToast(requireContext(),resources.getString(R.string.str_not_enter_otp))
            return false
        }
        return true
    }
    private fun confirmInput(): Boolean {
        if (!validatePassword() || !validateConfirmPassword() || !validateNotMatchPassword()) {
            return false
        }
        return true
    }

    private fun hideTextPhone(phone:String):String{
        return if (phone.length<10 || phone.length>10){
            ""
        }else{
            phone.replaceRange(3,8,"*****")
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


}