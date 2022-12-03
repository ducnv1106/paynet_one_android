package com.paynetone.counter.functions.payment;

import com.core.base.viper.Interactor;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

public class PaymentInteractor extends Interactor<PaymentContract.Presenter>
        implements PaymentContract.Interactor {

    public PaymentInteractor(PaymentContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void orderAdd(OrderAddRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.orderAdd(request, callback);
    }
}
