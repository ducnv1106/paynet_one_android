package com.paynetone.counter.personal;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.paynetone.counter.main.MainInteractor;
import com.paynetone.counter.model.EmployeeModel;
import com.paynetone.counter.model.MerchantBalance;
import com.paynetone.counter.model.PaynetGetBalanceByIdResponse;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.BaseRequest;
import com.paynetone.counter.model.request.PINAddRequest;
import com.paynetone.counter.model.request.TranSearchRequest;
import com.paynetone.counter.model.response.PINCodeResponse;
import com.paynetone.counter.model.response.ResponseMerchantBalance;
import com.paynetone.counter.model.response.TranSearchResponse;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;
import com.paynetone.counter.utils.PrefConst;
import com.paynetone.counter.utils.SharedPref;
import com.paynetone.counter.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PersonalPresenter extends Presenter<PersonalContract.View, PersonalContract.Interactor>
        implements PersonalContract.Presenter {

    public PersonalPresenter(ContainerView containerView) {
        super(containerView);
    }

    EmployeeModel employeeModel;

    @Override
    public void start() {
        employeeModel = SharedPref.getInstance(getViewContext()).getEmployeeModel();
        if (employeeModel !=null ){
            getBalance(employeeModel.getPaynetID());
        }

    }

    @Override
    public PersonalContract.Interactor onCreateInteractor() {
        return new PersonalInteractor(this);
    }

    @Override
    public PersonalContract.View onCreateView() {
        return PersonalFragment.getInstance();
    }

    @Override
    public void getBalance(int paynetId) {
        mView.showProgress();
        BaseRequest baseRequest = new BaseRequest();
        Log.e("TAG", "paynetId: "+ paynetId);
        baseRequest.setPaynetID(paynetId);
        mInteractor.getBalance(baseRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    ResponseMerchantBalance merchantBalance = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<ResponseMerchantBalance>() {
                    }.getType());
                    mView.showBalance(merchantBalance.getMerchantBalances());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                mView.hideProgress();
            }
        });
    }

    @Override
    public void paynetGetBalanceByID(int  requestId) {
        try{
            mView.showProgress();
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setID(requestId);
            mInteractor.paynetGetBalanceByID(baseRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mView.hideProgress();
                    if ("00".equals(response.body().getErrorCode())) {
                        ArrayList<PaynetGetBalanceByIdResponse> responses = NetWorkController.getGson().fromJson(response.body().getData(),
                                new TypeToken<ArrayList<PaynetGetBalanceByIdResponse>>() {}.getType());
                        mView.showManagerHanMuc(responses);
                    } else {
                        mView.showManagerHanMuc(new ArrayList<PaynetGetBalanceByIdResponse>());
                        mView.showAlertDialog(response.body().getMessage());
                    }
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    mView.showManagerHanMuc(new ArrayList<PaynetGetBalanceByIdResponse>());
                    super.onError(call, message);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void requestPINCode(PINAddRequest request) {
        try{
            mView.showProgress();
            mInteractor.requestPINCode(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mView.hideProgress();
                    if ("00".equals(response.body().getErrorCode())) {
                        PINCodeResponse pinCodeResponse = NetWorkController.getGson().fromJson(response.body().getData(),
                                new TypeToken<PINCodeResponse>(){}.getType());
                        mView.showPINCodeSuccess(pinCodeResponse);
                    } else {
                        Toast.showToast(getViewContext(),response.body().getMessage());
                    }
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
