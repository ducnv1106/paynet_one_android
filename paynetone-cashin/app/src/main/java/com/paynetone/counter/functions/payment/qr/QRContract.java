package com.paynetone.counter.functions.payment.qr;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.GetProviderResponse;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.model.response.OrderAddResponse;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.network.CommonCallback;

public interface QRContract {
    interface Interactor extends IInteractor<QRContract.Presenter> {
        void getByCode(String code, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<QRContract.Presenter> {
        void showOrder(OrderModel order);
    }

    interface Presenter extends IPresenter<QRContract.View, QRContract.Interactor> {
        OrderAddResponse getOrderAddResponse();

        OrderAddRequest getOrderAddRequest();

        GetProviderResponse getProviderResponse();

        void getByCode();
    }
}
