package com.paynetone.counter.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.core.utils.AppUtils
import com.google.gson.Gson
import com.paynetone.counter.R
import com.paynetone.counter.databinding.DialogChangePasswordBinding
import com.paynetone.counter.model.request.UpdatePasswordRequest
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.utils.*
import com.stepstone.stepper.VerificationError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DialogChangePassword : BaseDialogFragment<DialogChangePasswordBinding>() {

//    private val changePasswordViewModel by viewModels<ChangePasswordViewModel>()
//    private val loadingViewModel by activityViewModels<LoadingViewModel>()

    @Inject
    lateinit var gson: Gson

    override fun loadControlsAndResize(binding: DialogChangePasswordBinding) {
        binding.apply {

            rootView.setSingleClick {
                AppUtils.hideKeyboard(it)
            }

            btnUpdate.setSingleClick {
                if (confirmInput()) requestPinCode()
            }

            imgBack.setSingleClick {
                dismiss()
            }

            buttonPasswordToggleOld.setSingleClick {
                edtPasswordOld.passwordToggle(requireContext(),buttonPasswordToggleOld)
            }
            buttonPasswordToggleNew.setSingleClick {
                edtPasswordNew.passwordToggle(requireContext(),buttonPasswordToggleNew)
            }
            buttonConfirmPasswordToggleNew.setSingleClick {
                edtConfirmPasswordNew.passwordToggle(requireContext(),buttonConfirmPasswordToggleNew)
            }
        }
    }

    override fun initView() {

    }

    private fun requestPinCode(): Disposable {
        val mobileNumber = SharedPref.getInstance(requireContext()).employeeModel.mobileNumber
        val oldPassword = binding.edtPasswordOld.text.toString()
        val newPassword = binding.edtPasswordNew.text.toString()
        showProgressDialog()
        return NetWorkController.requestChangePassword(UpdatePasswordRequest(mobileNumber,password = oldPassword,passwordNew = newPassword))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00"){
                    val successDialog =  SuccessPaymentDialog.getInstance(getString(R.string.str_change_password_success))
                    successDialog.setCallBackListener(object : SuccessPaymentDialog.CallBackListener{
                        override fun onCloseClicked() {
                            this@DialogChangePassword.dismiss()
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

    private fun validateCurrentPassword():Boolean{
        val currentPassword = binding.edtPasswordOld.text.toString()
        if (currentPassword.isBlank()){
            toast(resources.getString(R.string.str_not_entered_current_password))
            return false
        }
        return true
    }
    private fun validatePassword():Boolean{
        val password = binding.edtPasswordNew.text.toString()
        if (password.isBlank()){
            toast(resources.getString(R.string.str_not_entered_new_password))
            return false
        }
        return true
    }

    private fun validateConfirmPassword():Boolean{
        val password = binding.edtConfirmPasswordNew.text.toString()
        if (password.isBlank()){
            toast(resources.getString(R.string.str_not_entered_confirm_password))
            return false
        }
        if (password.length < 6 || password.length > 50) {
            toast(resources.getString(R.string.str_message_field_password_invalid))
            return false
        }
        if (!Utils.passwordValidation(password)) {
            toast(resources.getString(R.string.str_message_field_password_not_strong))
            return false
        }
        return true
    }

    private fun validateNotMatchPassword() : Boolean{
        val password = binding.edtPasswordNew.text.toString()
        val confirmPassword = binding.edtConfirmPasswordNew.text.toString()
        if (password != confirmPassword){
            toast(resources.getString(R.string.str_not_match_password))
            return false
        }
        return true
    }
    private fun validateOldEqualsNewPassword():Boolean{
        val oldPassword = binding.edtPasswordOld.text.toString()
        val newPassword = binding.edtConfirmPasswordNew.text.toString()
        if (oldPassword == newPassword){
            toast(resources.getString(R.string.str_old_equals_new_password))
            return false
        }
        return true
    }


    private fun confirmInput(): Boolean {
        if (!validateCurrentPassword() || !validatePassword()  || !validateConfirmPassword() || !validateNotMatchPassword() || !validateOldEqualsNewPassword()) {
            return false
        }
        return true
    }


    override fun initStyle(): Int = R.style.FullScreenDialog

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogChangePasswordBinding = DialogChangePasswordBinding.inflate(inflater,container,false)
}