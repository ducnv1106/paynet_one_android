package com.paynetone.counter.login;

import com.core.base.viper.Interactor;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.BaseRequest;
import com.paynetone.counter.model.request.LoginRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

public class LoginInteractor extends Interactor<LoginContract.Presenter>
        implements LoginContract.Interactor {

    public LoginInteractor(LoginContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void login(LoginRequest loginRequest, CommonCallback<SimpleResult> callback) {
        NetWorkController.login(loginRequest, callback);
    }

    @Override
    public void getPaynet(BaseRequest baseRequest, CommonCallback<SimpleResult> callback) {
        NetWorkController.getPaynet(baseRequest,callback);
    }
}

