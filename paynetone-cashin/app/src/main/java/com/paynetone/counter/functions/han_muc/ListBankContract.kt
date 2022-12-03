package com.paynetone.counter.functions.han_muc

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView

class ListBankContract {

    interface Interactor : IInteractor<Presenter> {

    }

    interface View : PresentView<Presenter> {

    }

    interface Presenter : IPresenter<View, Interactor> {

    }
}