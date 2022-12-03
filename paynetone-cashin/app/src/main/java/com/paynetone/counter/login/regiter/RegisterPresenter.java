package com.paynetone.counter.login.regiter;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class RegisterPresenter  extends Presenter<RegisterContract.View, RegisterContract.Interactor>
        implements RegisterContract.Presenter{

    public RegisterPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public RegisterContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public RegisterContract.View onCreateView() {
        return RegisterFragment.getInstance();
    }
}
