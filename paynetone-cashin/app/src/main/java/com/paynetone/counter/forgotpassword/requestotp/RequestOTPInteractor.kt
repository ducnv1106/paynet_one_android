package com.paynetone.counter.forgotpassword.requestotp

import com.core.base.viper.Interactor
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.network.CommonCallback
import com.paynetone.counter.network.NetWorkController

internal class RequestOTPInteractor(presenter: RequestOTPContract.Presenter) : Interactor<RequestOTPContract.Presenter>(presenter),
    RequestOTPContract.Interactor {
    override fun getOTP(mobile: String, isForget: String, callback: CommonCallback<SimpleResult>?) {
        NetWorkController.getOTP(mobile,isForget,callback)
    }
}