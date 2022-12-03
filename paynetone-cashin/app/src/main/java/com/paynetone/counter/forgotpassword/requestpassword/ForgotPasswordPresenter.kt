package com.paynetone.counter.forgotpassword.requestpassword

import android.app.Activity
import android.util.Log
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.model.request.ChangePassByOTPRequest
import com.paynetone.counter.model.request.RequestOtp
import com.paynetone.counter.model.request.UpdatePasswordRequest
import com.paynetone.counter.network.CommonCallback
import com.paynetone.counter.utils.Toast
import retrofit2.Call
import retrofit2.Response

class ForgotPasswordPresenter(containerView: ContainerView, private val phoneNumber:String)  : Presenter<ForgotPasswordContract.View, ForgotPasswordContract.Interactor>(containerView),
    ForgotPasswordContract.Presenter {
    override fun updatePassword(request: ChangePassByOTPRequest) {
        mView.showProgress()
        mInteractor.updatePassword(request,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                super.onSuccess(call, response)
                if ("00" == response.body()?.errorCode) {
                    mView.updatePasswordSuccess()
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

    override fun requestOtp(requestOtp: RequestOtp) {
        mView.showProgress()
        mInteractor.requestOtp(requestOtp,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                super.onSuccess(call, response)
                mView.hideProgress()
                if ("00" == response.body()?.errorCode) {
                   mView.requestOtpSuccess()
                } else {
                    mView.showError(response.body()?.message)
                }
            }

            override fun onError(call: Call<SimpleResult>, message: String) {
                super.onError(call, message)
                mView.hideProgress()
                mView.showError(message)
            }
        })
    }



    override fun phoneNumber(): String = phoneNumber

    override fun getOTP(mobile: String, isForget: String) {
        mView.showProgress()
        mInteractor.getOTP(mobile, isForget,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                super.onSuccess(call, response)
                if ("00" == response.body()?.errorCode) {
                    mView.hideProgress()
                    mView.showSuccessOTP()
                } else {
                    mView.hideProgress()
                    mView.showError(response.body()?.message)
                }
            }

            override fun onError(call: Call<SimpleResult>, message: String) {
                super.onError(call, message)
                mView.showError(message)
                mView.hideProgress()
            }
        })
    }

    override fun start() {

    }

    override fun onCreateInteractor(): ForgotPasswordContract.Interactor = ForgotPasswordInteractor(this)

    override fun onCreateView(): ForgotPasswordContract.View = ForgotPasswordFragment.instance

}