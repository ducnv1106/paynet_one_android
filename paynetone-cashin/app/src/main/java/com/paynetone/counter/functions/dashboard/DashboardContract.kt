package com.paynetone.counter.functions.dashboard

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.model.request.DashboardRequest
import com.paynetone.counter.model.request.PINAddRequest
import com.paynetone.counter.model.request.TopupAddressRequest
import com.paynetone.counter.model.response.DashboardData
import com.paynetone.counter.model.response.Statistical
import com.paynetone.counter.network.CommonCallback

class DashboardContract {

    interface Interactor : IInteractor<Presenter> {
        fun requestDashboard(request: DashboardRequest, callback: CommonCallback<SimpleResult>?)
    }

    interface View : PresentView<Presenter> {
        fun requestError(message:String)
        fun showDashboardDataDay(response:ArrayList<DashboardData>?)
        fun showStatisticalDay(response: ArrayList<Statistical>? )
        fun showDashboardDataMonth(response:ArrayList<DashboardData>?)
        fun showStatisticalMonth(response: ArrayList<Statistical>? )

    }

    interface Presenter : IPresenter<View, Interactor> {
        fun requestDashboardDay(request: DashboardRequest)
        fun requestDashboardMonth(request: DashboardRequest)
    }
}