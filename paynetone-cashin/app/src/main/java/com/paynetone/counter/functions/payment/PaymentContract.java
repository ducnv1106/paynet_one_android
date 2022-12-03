package com.paynetone.counter.functions.payment;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.LoginRequest;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.network.CommonCallback;

public interface PaymentContract {
    interface Interactor extends IInteractor<PaymentContract.Presenter> {
        void orderAdd(OrderAddRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<PaymentContract.Presenter> {
    }

    interface Presenter extends IPresenter<PaymentContract.View, PaymentContract.Interactor> {
        OrderAddRequest getOrderAddRequest();

        void orderAdd(OrderAddRequest request);
    }
}
