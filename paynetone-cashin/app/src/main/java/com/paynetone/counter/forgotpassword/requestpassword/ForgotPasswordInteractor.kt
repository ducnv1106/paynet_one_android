package com.paynetone.counter.forgotpassword.requestpassword

import com.core.base.viper.Interactor
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.model.request.ChangePassByOTPRequest
import com.paynetone.counter.model.request.RequestOtp
import com.paynetone.counter.model.request.UpdatePasswordRequest
import com.paynetone.counter.network.CommonCallback
import com.paynetone.counter.network.NetWorkController

internal class ForgotPasswordInteractor(presenter: ForgotPasswordContract.Presenter) : Interactor<ForgotPasswordContract.Presenter>(presenter),
    ForgotPasswordContract.Interactor {
    override fun updatePassword(request: ChangePassByOTPRequest, callback: CommonCallback<SimpleResult>) {
        NetWorkController.updatePassword(request,callback)
    }

    override fun requestOtp(requestOtp: RequestOtp, callback: CommonCallback<SimpleResult>) {
        NetWorkController.requestOtp(requestOtp,callback)
    }

    override fun getOTP(mobile: String, isForget: String, callback: CommonCallback<SimpleResult>?) {
        NetWorkController.getOTP(mobile,isForget,callback)
    }


}