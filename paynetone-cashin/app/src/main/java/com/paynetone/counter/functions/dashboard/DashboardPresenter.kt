package com.paynetone.counter.functions.dashboard

import android.app.Activity
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.functions.service.ServiceContract
import com.paynetone.counter.functions.service.ServiceFragment
import com.paynetone.counter.functions.service.ServiceInteractor
import com.paynetone.counter.model.SimpleResult
import com.paynetone.counter.model.request.DashboardRequest
import com.paynetone.counter.model.response.DashboardData
import com.paynetone.counter.model.response.DashboardResponse
import com.paynetone.counter.network.CommonCallback
import com.paynetone.counter.network.NetWorkController
import retrofit2.Call
import retrofit2.Response

class DashboardPresenter(containerView: ContainerView) : Presenter<DashboardContract.View, DashboardContract.Interactor>(containerView), DashboardContract.Presenter {
    override fun start() {

    }

    override fun onCreateInteractor(): DashboardContract.Interactor = DashboardInteractor(this)

    override fun onCreateView(): DashboardContract.View = DashboardFragment.instance

    override fun requestDashboardDay(request: DashboardRequest) {
        mView.showProgress()
        mInteractor.requestDashboard(request,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                super.onSuccess(call, response)
                mView.hideProgress()
                if ("00" == response.body()?.errorCode) {
                    val result = NetWorkController.getGson().fromJson<DashboardResponse>(response.body()?.data,object : TypeToken<DashboardResponse>(){}.type)
                    mView.showDashboardDataDay(result.getDataDashboard())
                    mView.showStatisticalDay(result.getStatisticals())
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
    override fun requestDashboardMonth(request: DashboardRequest) {
        mView.showProgress()
        mInteractor.requestDashboard(request,object : CommonCallback<SimpleResult>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                super.onSuccess(call, response)
                mView.hideProgress()
                if ("00" == response.body()?.errorCode) {
                    val result = NetWorkController.getGson().fromJson<DashboardResponse>(response.body()?.data,object : TypeToken<DashboardResponse>(){}.type)
                    mView.showDashboardDataMonth(result.getDataDashboard())
                    mView.showStatisticalMonth(result.getStatisticals())
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