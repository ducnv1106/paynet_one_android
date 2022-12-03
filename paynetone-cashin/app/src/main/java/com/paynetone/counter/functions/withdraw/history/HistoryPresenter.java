package com.paynetone.counter.functions.withdraw.history;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.paynetone.counter.model.EmployeeModel;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.OrderSearchRequest;
import com.paynetone.counter.model.request.WithdrawSearchRequest;
import com.paynetone.counter.model.response.WithdrawSearchResponse;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;
import com.paynetone.counter.utils.SharedPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HistoryPresenter  extends Presenter<HistoryContract.View, HistoryContract.Interactor>
        implements HistoryContract.Presenter{

    SharedPref sharedPref;
    PaynetModel paynetModel;
    String balanceType;

    public HistoryPresenter(ContainerView containerView,String balanceType) {
        super(containerView);
        this.balanceType = balanceType;
    }


    @Override
    public void start() {
        sharedPref = new SharedPref(getViewContext());
        paynetModel = sharedPref.getPaynet();
        withdrawSearch();
    }


    @Override
    public HistoryContract.Interactor onCreateInteractor() {
        return new HistoryInteractor(this);
    }

    @Override
    public HistoryContract.View onCreateView() {
        return HistoryFragment.getInstance();
    }

    @Override
    public void withdrawSearch() {
        WithdrawSearchRequest request = new WithdrawSearchRequest(paynetModel.getMerchantID(),balanceType);
        mInteractor.withdrawSearch(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<WithdrawSearchResponse> withdrawSearchResponses = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<WithdrawSearchResponse>>() {
                    }.getType());
                    mView.showWithdraws(withdrawSearchResponses);
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
