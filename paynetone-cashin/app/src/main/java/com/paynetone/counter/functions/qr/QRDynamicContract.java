package com.paynetone.counter.functions.qr;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.PaymentModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.GetProviderResponse;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.network.CommonCallback;

public interface QRDynamicContract {
    interface Interactor extends IInteractor<QRDynamicContract.Presenter> {
        void orderAdd(OrderAddRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<QRDynamicContract.Presenter> {
    }

    interface Presenter extends IPresenter<QRDynamicContract.View, QRDynamicContract.Interactor> {
        GetProviderResponse getProviderResponse();

        void orderAdd(OrderAddRequest request);

    }
}
