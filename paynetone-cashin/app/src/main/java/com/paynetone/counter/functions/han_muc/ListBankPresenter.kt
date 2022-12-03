package com.paynetone.counter.functions.han_muc

import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView

class ListBankPresenter(containerView: ContainerView) : Presenter<ListBankContract.View, ListBankContract.Interactor>(containerView),
    ListBankContract.Presenter {

    override fun start() {

    }

    override fun onCreateInteractor(): ListBankContract.Interactor = ListBankInteractor(this)

    override fun onCreateView(): ListBankContract.View = ListBankFragment.instance

}