package com.paynetone.counter.functions.dashboard

import com.core.base.viper.Interactor
import com.paynetone.counter.functions.service.ServiceContract
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.model.request.DashboardRequest
import com.paynetone.counter.model.request.PINAddRequest
import com.paynetone.counter.model.request.TopupAddressRequest
import com.paynetone.counter.network.CommonCallback
import com.paynetone.counter.network.NetWorkController

class DashboardInteractor(presenter: DashboardContract.Presenter) : Interactor<DashboardContract.Presenter>(presenter),
    DashboardContract.Interactor {
    override fun requestDashboard(request: DashboardRequest, callback: CommonCallback<SimpleResult>?) {
        NetWorkController.questDashboard(request,callback)
    }


}