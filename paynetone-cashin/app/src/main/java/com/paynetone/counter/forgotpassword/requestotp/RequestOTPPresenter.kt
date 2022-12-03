package com.paynetone.counter.forgotpassword.requestotp

import android.app.Activity
import android.util.Log
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.network.CommonCallback
import retrofit2.Call
import retrofit2.Response


class RequestOTPPresenter(containerView: ContainerView) :
    Presenter<RequestOTPContract.View, RequestOTPContract.Interactor>(containerView),
    RequestOTPContract.Presenter {

    override fun getOTP(mobile: String, isForget: String) {
        mView.showProgress()
        mInteractor.getOTP(mobile, isForget,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
                override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                    super.onSuccess(call, response)
                    if ("00" == response.body()?.errorCode) {
                        mView.showSuccessOTP()
                    } else {
                        mView.showError(response.body()?.message)
                    }
                }

                override fun onError(call: Call<SimpleResult>, message: String) {
                    super.onError(call, message)
                    mView.showError(message)
                }
            })

    }

    override fun start() {

    }

    override fun onCreateInteractor(): RequestOTPContract.Interactor = RequestOTPInteractor(this)

    override fun onCreateView(): RequestOTPContract.View = RequestOTPFragment.instance
}