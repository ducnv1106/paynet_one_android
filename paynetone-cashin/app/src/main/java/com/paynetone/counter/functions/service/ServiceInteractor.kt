package com.paynetone.counter.functions.service

import com.core.base.viper.Interactor
import com.paynetone.counter.forgotpassword.requestotp.RequestOTPContract
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.model.request.PINAddRequest
import com.paynetone.counter.model.request.PartNerAddressRequest
import com.paynetone.counter.model.request.TopupAddressRequest
import com.paynetone.counter.network.CommonCallback
import com.paynetone.counter.network.NetWorkController

class ServiceInteractor(presenter: ServiceContract.Presenter) : Interactor<ServiceContract.Presenter>(presenter),
    ServiceContract.Interactor {
    override fun topUpAddress(request: TopupAddressRequest, callback: CommonCallback<SimpleResult>?) {
        NetWorkController.requestViTopupArress(request,callback)
    }

    override fun requestVerifyPinCode(request: PINAddRequest, callback: CommonCallback<SimpleResult>?) {
        NetWorkController.requestVerifyPinCode(request,callback)
    }

    override fun requestVBookingAddress(request: TopupAddressRequest, callback: CommonCallback<SimpleResult>?) {
        NetWorkController.requestVBookingArress(request,callback)
    }

    override fun requestPartnerAddress(request: PartNerAddressRequest, callback: CommonCallback<SimpleResult>?) {
        NetWorkController.requestPartnerAddress(request,callback)
    }

}