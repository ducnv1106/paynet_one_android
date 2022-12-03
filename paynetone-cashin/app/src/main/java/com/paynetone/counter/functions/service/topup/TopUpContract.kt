package com.paynetone.counter.functions.service.topup

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView

class TopUpContract {

    interface Interactor : IInteractor<Presenter> {
    }

    interface View : PresentView<Presenter> {


    }

    interface Presenter : IPresenter<View, Interactor> {

        fun urlTopUpAddress():String
        fun amountHanMuc():Long

    }
}