package com.paynetone.counter.forgotpassword.requestpassword

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.model.request.ChangePassByOTPRequest
import com.paynetone.counter.model.request.RequestOtp
import com.paynetone.counter.model.request.UpdatePasswordRequest
import com.paynetone.counter.network.CommonCallback

interface ForgotPasswordContract {
    interface Interactor : IInteractor<Presenter> {
        fun updatePassword(request: ChangePassByOTPRequest, callback: CommonCallback<SimpleResult>)
        fun requestOtp(requestOtp: RequestOtp, callback: CommonCallback<SimpleResult>)
        fun getOTP(mobile: String, isForget: String, callback: CommonCallback<SimpleResult>?)
    }

    interface View : PresentView<Presenter> {
        fun showError(message: String?)
        fun showSuccessOTP()
        fun updatePasswordSuccess()
        fun requestOtpSuccess()

    }

    interface Presenter : IPresenter<View, Interactor> {

        fun updatePassword(request: ChangePassByOTPRequest)
        fun requestOtp(requestOtp: RequestOtp)

        fun phoneNumber() : String
        fun getOTP(mobile: String, isForget: String)

    }

}