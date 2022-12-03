package com.paynetone.counter.functions.service

import android.app.Activity
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.forgotpassword.requestotp.RequestOTPContract
import com.paynetone.counter.forgotpassword.requestotp.RequestOTPFragment
import com.paynetone.counter.forgotpassword.requestotp.RequestOTPInteractor
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.model.request.PINAddRequest
import com.paynetone.counter.model.request.PartNerAddressRequest
import com.paynetone.counter.model.request.TopupAddressRequest
import com.paynetone.counter.model.response.TopUpAddressResponse
import com.paynetone.counter.network.CommonCallback
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.utils.Toast
import retrofit2.Call
import retrofit2.Response
class ServicePresenter(containerView: ContainerView) :
    Presenter<ServiceContract.View, ServiceContract.Interactor>(containerView),
    ServiceContract.Presenter {


    override fun start() {

    }

    override fun onCreateInteractor(): ServiceContract.Interactor = ServiceInteractor(this)

    override fun onCreateView(): ServiceContract.View = ServiceFragment.instance

    override fun topUpAddress(request: TopupAddressRequest) {
        mView.showProgress()
        mInteractor.topUpAddress(request,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                super.onSuccess(call, response)
                mView.hideProgress()
                if ("00" == response.body()?.errorCode) {
                    val topUpAddressResponse = NetWorkController.getGson().fromJson<TopUpAddressResponse>(response.body()?.data,object :
                        TypeToken<TopUpAddressResponse>(){}.type)
                    mView.requestTopUpAddressSuccess(topUpAddressResponse.returnUrl)
                } else {
                    mView.requestError(response.body()?.message ?: "")
                }
            }

            override fun onError(call: Call<SimpleResult>, message: String) {
                mView.hideProgress()
                mView.requestError(message)

            }
        })
    }

    override fun requestVerifyPinCode(request: PINAddRequest) {
        mView.showProgress()
        mInteractor.requestVerifyPinCode(request,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                super.onSuccess(call, response)
                mView.hideProgress()
                if ("00" == response.body()?.errorCode) {
                    mView.requestVerifyPINCodeSuccess()
                } else {
                    mView.requestError(response.body()?.message ?: "Có lỗi xảy ra")
                }
            }

            override fun onError(call: Call<SimpleResult>, message: String) {
                mView.hideProgress()
                mView.requestError(message)

            }
        })
    }

     override fun requestVBookingAddress(request: TopupAddressRequest) {
         mView.showProgress()
         mInteractor.requestVBookingAddress(request,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
             override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                 super.onSuccess(call, response)
                 mView.hideProgress()
                 if ("00" == response.body()?.errorCode) {
                    val topUpAddressResponse = NetWorkController.getGson().fromJson<TopUpAddressResponse>(response.body()?.data,object :
                        TypeToken<TopUpAddressResponse>(){}.type)
                    mView.requestVBoonKingSuccess(topUpAddressResponse.returnUrl)
                 } else {
                     mView.requestError(response.body()?.message ?: "Có lỗi xảy ra")
                 }
             }

             override fun onError(call: Call<SimpleResult>, message: String) {
                 mView.hideProgress()
                 mView.requestError(message)

             }
         })
     }

    override fun requestPartnerAddress(request: PartNerAddressRequest) {
        mView.showProgress()
        mInteractor.requestPartnerAddress(request,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                super.onSuccess(call, response)
                mView.hideProgress()
                if ("00" == response.body()?.errorCode) {
                    val result = NetWorkController.getGson().fromJson<TopUpAddressResponse>(response.body()?.data,object : TypeToken<TopUpAddressResponse>(){}.type)
                    mView.requestPartNerSuccess(result.returnUrl)
                } else {
                    mView.requestError(response.body()?.message ?: "Có lỗi xảy ra")
                }
            }

            override fun onError(call: Call<SimpleResult>, message: String) {
                mView.hideProgress()
                mView.requestError(message)

            }
        })
    }
}