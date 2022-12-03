package com.paynetone.counter.forgotpassword.requestotp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.core.base.viper.ViewFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.FragmentRequestOtpBinding
import com.paynetone.counter.forgotpassword.requestpassword.ForgotPasswordActivity
import com.paynetone.counter.utils.Constants.TYPE_GET_OTP_FORGOT_PASSWORD
import com.paynetone.counter.utils.ExtraConst.Companion.EXTRA_PHONE_NUMBER
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.hideKeyboard
import com.paynetone.counter.utils.setSingleClick
import kotlinx.android.synthetic.main.fragment_request_otp.*
import org.apache.commons.lang3.math.NumberUtils


class RequestOTPFragment : ViewFragment<RequestOTPContract.Presenter>(),RequestOTPContract.View {

    private lateinit var binding:FragmentRequestOtpBinding

    companion object {
        val instance: RequestOTPFragment
            get() = RequestOTPFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),layoutId,container,false)
        binding.lifecycleOwner=this
        initView()
        return binding.root
    }

    private fun initView(){
        onListener()
    }

    private fun onListener(){
        binding.apply {
            ivBack.setSingleClick {
                activity?.finish()
            }
            btnSendRequest.setSingleClick {
                val mobileNumber = edtMobileNumber.text.toString()
                if (mobileNumber.isBlank()){
                    Toast.showToast(requireContext(), resources.getString(R.string.str_did_not_phone_number))
                    return@setSingleClick
                }
                if (!NumberUtils.isDigits(mobileNumber)) {
                    Toast.showToast(requireContext(),R.string.error_warning_phone)
                    return@setSingleClick
                }
                if (mobileNumber.length != 10) {
                    Toast.showToast(requireContext(),R.string.error_warning_phone)
                    return@setSingleClick
                }
                if (mobileNumber.substring(0,1) != "0"){
                    Toast.showToast(requireContext(),R.string.error_warning_phone)
                    return@setSingleClick
                }
                mPresenter.getOTP(mobileNumber,TYPE_GET_OTP_FORGOT_PASSWORD)
            }
            tvLogin.setSingleClick {
                activity?.finish()
            }
            rootView.setSingleClick {
                it.hideKeyboard()
            }

        }
    }


    override fun getLayoutId(): Int  = R.layout.fragment_request_otp


    override fun showError(message: String?) {
    }

    override fun showSuccessOTP() {
        val intent = Intent(requireActivity(), ForgotPasswordActivity::class.java)
        intent.putExtra(EXTRA_PHONE_NUMBER, binding.edtMobileNumber.text.toString())
        startActivity(intent)
        activity?.finish()
    }

}