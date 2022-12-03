package com.paynetone.counter.functions.service.topup

import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView

class TopUpPresenter(containerView: ContainerView, val url:String, var amount:Long) :
    Presenter<TopUpContract.View, TopUpContract.Interactor>(containerView),
    TopUpContract.Presenter {


    override fun start() {


    }

    override fun onCreateInteractor(): TopUpContract.Interactor = TopUpInteractor(this)

    override fun onCreateView(): TopUpContract.View = TopUpFragment.instance

    override fun urlTopUpAddress(): String  = url

    override fun amountHanMuc(): Long = amount

}