package com.paynetone.counter.main;

import android.app.Activity;
import android.util.Log;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.paynetone.counter.home.HomeInteractor;
import com.paynetone.counter.model.EmployeeModel;
import com.paynetone.counter.model.MerchantBalance;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.BaseRequest;
import com.paynetone.counter.model.request.GetProviderResponse;
import com.paynetone.counter.model.request.RequestPayNetHasChildren;
import com.paynetone.counter.model.response.BannerResponse;
import com.paynetone.counter.model.response.PayNetHasChildrenResponse;
import com.paynetone.counter.model.response.ResponseMerchantBalance;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.SharedPref;
import com.paynetone.counter.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainPresenter extends Presenter<MainContract.View, MainContract.Interactor>
        implements MainContract.Presenter{

    SharedPref sharedPref;
    Activity activity;
    EmployeeModel employeeModel;


    public MainPresenter(ContainerView containerView) {
        super(containerView);
        this.activity = (Activity) containerView;
    }

    @Override
    public void start() {
        sharedPref = new SharedPref(activity);
        employeeModel = sharedPref.getEmployeeModel();
        getBalance();
        requestProvider();
        requestAddressPayNetID();
        requestBanner();
        requestPayNetHasChildren();
    }

    @Override
    public MainContract.Interactor onCreateInteractor() {
        return new MainInteractor(this);
    }

    @Override
    public MainContract.View onCreateView() {
        return MainFragment.getInstance();
    }

    @Override
    public void getBalance() {
        try {
            BaseRequest baseRequest = new BaseRequest();
            if (employeeModel != null ){
                baseRequest.setPaynetID(employeeModel.getPaynetID());
                mInteractor.getBalance(baseRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);

                        if ("00".equals(response.body().getErrorCode())) {
                            ResponseMerchantBalance merchantBalance = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<ResponseMerchantBalance>() {
                            }.getType());
                            mView.showBalance(merchantBalance.getMerchantBalances());
                        }else {
//                            Toast.showToast(getViewContext(),response.body().getMessage());
                        }
                    }

                    @Override
                    protected void onError(Call<SimpleResult> call, String message) {
//                        super.onError(call, message);
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void requestProvider() {
        mView.showProgress();
        mInteractor.requestProvider( new CommonCallback<SimpleResult>((Activity) mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();

                if ("00".equals(response.body().getErrorCode())) {
                    ArrayList<GetProviderResponse> responses = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<ArrayList<GetProviderResponse>>() {
                    }.getType());
                    mView.showSuccessProvider(responses);
                }else {
                    Toast.showToast(getViewContext(),response.body().getMessage());
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
    public void requestAddressPayNetID() {
        try {
            BaseRequest baseRequest = new BaseRequest();
            if (employeeModel != null ){
                baseRequest.setID(employeeModel.getPaynetID());
                mInteractor.requestAddressPayNetID(baseRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);

                        if ("00".equals(response.body().getErrorCode())) {
                            sharedPref.putString(Constants.KEY_ADDRESS_PAYNET_ID,response.body().getData());
                        }else {
                            Toast.showToast(getViewContext(),response.body().getMessage());
                        }
                    }

                    @Override
                    protected void onError(Call<SimpleResult> call, String message) {
                        super.onError(call, message);
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void requestBanner() {
        try {
            mInteractor.requestBanner( new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    if ("00".equals(response.body().getErrorCode())) {
                        ArrayList<BannerResponse> responses = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<ArrayList<BannerResponse>>() {
                        }.getType());
                        mView.showBanner(responses);
                    }else {
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

    @Override
    public void requestPayNetHasChildren() {
        try {
            RequestPayNetHasChildren request = new RequestPayNetHasChildren(employeeModel.getPaynetID());
            mInteractor.requestPayNetHasChildren(request,new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    if ("00".equals(response.body().getErrorCode())) {
                        sharedPref.putString(Constants.KEY_PAYNET_HAS_CHILDREN,response.body().getData());

                    }else {
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
