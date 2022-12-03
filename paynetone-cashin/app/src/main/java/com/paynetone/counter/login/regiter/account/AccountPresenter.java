package com.paynetone.counter.login.regiter.account;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.interfaces.RegisterPassData;
import com.paynetone.counter.model.EmployeeModel;
import com.paynetone.counter.model.RegisterPassDataModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.EmployeeAddNewRequest;
import com.paynetone.counter.model.request.LoginRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;
import com.paynetone.counter.utils.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

public class AccountPresenter extends Presenter<AccountContract.View, AccountContract.Interactor>
        implements AccountContract.Presenter {

    RegisterPassData registerPassData;

    public AccountPresenter(ContainerView containerView, RegisterPassData registerPassData) {
        super(containerView);

        this.registerPassData = registerPassData;
    }

    @Override
    public void start() {

    }

    @Override
    public AccountContract.Interactor onCreateInteractor() {
        return new AccountInteractor(this);
    }

    @Override
    public AccountContract.View onCreateView() {
        return AccountFragment.getInstance();
    }

    @Override
    public void employeeAdd(EmployeeAddNewRequest request) {
        mView.showProgress();
        mInteractor.employeeAdd(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    mView.showSuccess();
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showError(message);
            }
        });
    }

    @Override
    public void getOTP(String mobile,String isForget) {
        mView.showProgress();
        mInteractor.getOTP(mobile,isForget, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    mView.showSuccessOTP();
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showError(message);
            }
        });
    }

    @Override
    public void saveData(RegisterPassDataModel dataModel) {
        registerPassData.saveData(dataModel);
    }
}
