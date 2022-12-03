package com.paynetone.counter.functions.qr;

import com.core.base.viper.Interactor;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

public class QRDynamicInteractor extends Interactor<QRDynamicContract.Presenter>
        implements QRDynamicContract.Interactor{

    public QRDynamicInteractor(QRDynamicContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void orderAdd(OrderAddRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.orderAdd(request, callback);
    }
}
