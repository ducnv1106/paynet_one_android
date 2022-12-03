package com.paynetone.counter.login.regiter;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

public interface RegisterContract {
    interface Interactor extends IInteractor<RegisterContract.Presenter> {
    }

    interface View extends PresentView<RegisterContract.Presenter> {
    }

    interface Presenter extends IPresenter<RegisterContract.View, RegisterContract.Interactor> {

    }
}
