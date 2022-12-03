package com.paynetone.counter.functions.qr;

import android.app.Activity;
import android.util.Log;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.functions.payment.PaymentInteractor;
import com.paynetone.counter.functions.payment.qr.QRPresenter;
import com.paynetone.counter.model.PaymentModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.GetProviderResponse;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.model.response.OrderAddResponse;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

import retrofit2.Call;
import retrofit2.Response;

public class QRDynamicPresenter extends Presenter<QRDynamicContract.View, QRDynamicContract.Interactor>
        implements QRDynamicContract.Presenter {

    GetProviderResponse providerResponse;

    public QRDynamicPresenter(ContainerView containerView,GetProviderResponse providerResponse) {
        super(containerView);
        this.providerResponse = providerResponse;
    }

    @Override
    public void start() {

    }

    @Override
    public QRDynamicContract.Interactor onCreateInteractor() {
        return new QRDynamicInteractor(this);
    }

    @Override
    public QRDynamicContract.View onCreateView() {
        return QRDynamicFragment.getInstance();
    }

    @Override
    public GetProviderResponse getProviderResponse() {
        return providerResponse;
    }

    @Override
    public void orderAdd(OrderAddRequest request) {
        mView.showProgress();
        mInteractor.orderAdd(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    OrderAddResponse orderAddResponse = NetWorkController.getGson().fromJson(response.body().getData(), OrderAddResponse.class);
                    new QRPresenter(mContainerView, orderAddResponse, request,providerResponse).pushView();
                } else {
                    mView.showAlertDialog(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });

    }
}
