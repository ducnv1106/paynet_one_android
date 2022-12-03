package com.paynetone.counter.login;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.BaseRequest;
import com.paynetone.counter.model.request.LoginRequest;
import com.paynetone.counter.network.CommonCallback;

public interface LoginContract {
    interface Interactor extends IInteractor<Presenter> {
        void login(LoginRequest loginRequest, CommonCallback<SimpleResult> callback);
        void getPaynet(BaseRequest baseRequest, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void goHome();
        void clearInputValidate();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void login(String phone, String passWord,String token);
    }
}
