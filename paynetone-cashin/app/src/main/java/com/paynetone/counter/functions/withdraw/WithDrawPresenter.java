package com.paynetone.counter.functions.withdraw;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.paynetone.counter.model.MerchantModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.PaynetGetByParentRequest;
import com.paynetone.counter.model.request.WithdrawRequest;
import com.paynetone.counter.model.response.PaynetGetByParentResponse;
import com.paynetone.counter.model.response.WalletResponse;
import com.paynetone.counter.model.response.WithdrawResponse;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class WithDrawPresenter  extends Presenter<WithDrawContract.View, WithDrawContract.Interactor>
        implements WithDrawContract.Presenter {
    public WithDrawPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public WithDrawContract.Interactor onCreateInteractor() {
        return new WithDrawInteractor(this);
    }

    @Override
    public WithDrawContract.View onCreateView() {
        return WithDrawFragment.getInstance();
    }

    @Override
    public void withdraw(WithdrawRequest withdrawRequest) {
        mView.showProgress();
        mInteractor.withdraw(withdrawRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    WithdrawResponse withdrawResponse = NetWorkController.getGson().fromJson(response.body().getData(),WithdrawResponse.class);
                    mView.showSuccess(withdrawResponse.getRetRefNumber());
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
    public void getWallet() {
        mView.showProgress();
        mInteractor.getWallet( new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if ("00".equals(response.body().getErrorCode())) {
                    List<WalletResponse> walletResponseList = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<WalletResponse>>(){}.getType());
                    mView.showListWallet(walletResponseList);
                } else {
                    mView.showAlertDialog(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        });
    }

    @Override
    public void getByMobileNumber(String mobileNumber) {
        mInteractor.getByMobileNumber(mobileNumber, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    MerchantModel model = NetWorkController.getGson().fromJson(response.body().getData(), MerchantModel.class);
                    mView.showMerchant(model);
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
    public void paynetGetByParentRequest(int parentID) {
        try{
            mInteractor.paynetGetByParentRequest(new PaynetGetByParentRequest(parentID), new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    if ("00".equals(response.body().getErrorCode())) {
                        ArrayList<PaynetGetByParentResponse> responses = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<ArrayList<PaynetGetByParentResponse>>(){}.getType());
                        mView.showPaynetGetByParentRequest(responses);

                    } else {
                        mView.showPaynetGetByParentRequest(new ArrayList<>());
                    }
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    mView.showPaynetGetByParentRequest(new ArrayList<>());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
