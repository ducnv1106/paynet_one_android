package com.paynetone.counter.forgotpassword.requestotp

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.network.CommonCallback

class RequestOTPContract {

    interface Interactor : IInteractor<Presenter> {
        fun getOTP(mobile: String, isForget: String, callback: CommonCallback<SimpleResult>?)
    }

    interface View : PresentView<Presenter> {
        fun showError(message: String?)
        fun showSuccessOTP()
    }

    interface Presenter : IPresenter<View,Interactor> {
        fun getOTP(mobile: String, isForget: String)
    }
}