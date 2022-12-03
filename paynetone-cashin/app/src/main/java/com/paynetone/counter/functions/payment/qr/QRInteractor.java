package com.paynetone.counter.functions.payment.qr;

import com.core.base.viper.Interactor;
import com.paynetone.counter.functions.payment.PaymentContract;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

public class QRInteractor extends Interactor<QRContract.Presenter>
        implements QRContract.Interactor {

    public QRInteractor(QRContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getByCode(String code, CommonCallback<SimpleResult> callback) {
        NetWorkController.getByOrderCode(code, callback);
    }
}
