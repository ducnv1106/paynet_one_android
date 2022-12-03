package com.paynetone.counter.functions.payment.qr;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.GetProviderResponse;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.model.response.OrderAddResponse;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

import retrofit2.Call;
import retrofit2.Response;

public class QRPresenter  extends Presenter<QRContract.View, QRContract.Interactor>
        implements QRContract.Presenter {

    OrderAddResponse mOrderAddResponse;
    OrderAddRequest mOrderAddRequest;
    GetProviderResponse providerResponse;

    public QRPresenter(ContainerView containerView, OrderAddResponse orderAddResponse,OrderAddRequest orderAddRequest,GetProviderResponse providerResponse) {
        super(containerView);
        this.providerResponse = providerResponse;
        this.mOrderAddResponse = orderAddResponse;
        this.mOrderAddRequest = orderAddRequest;
    }


    @Override
    public OrderAddResponse getOrderAddResponse() {
        return mOrderAddResponse;
    }

    @Override
    public OrderAddRequest getOrderAddRequest() {
        return mOrderAddRequest;
    }

    @Override
    public GetProviderResponse getProviderResponse() {
        return providerResponse;
    }

    @Override
    public void getByCode() {
        mView.showProgress();
        mInteractor.getByCode(mOrderAddResponse.getOrderCode(), new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    OrderModel order = NetWorkController.getGson().fromJson(response.body().getData(), OrderModel.class);
                    mView.showOrder(order);
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

    @Override
    public void start() {

    }

    @Override
    public QRContract.Interactor onCreateInteractor() {
        return new QRInteractor(this);
    }

    @Override
    public QRContract.View onCreateView() {
        return QRFragment.getInstance();
    }
}
