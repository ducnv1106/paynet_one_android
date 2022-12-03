package com.paynetone.counter.functions.outward;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.paynetone.counter.model.EmployeeModel;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.OrderSearchRequest;
import com.paynetone.counter.model.request.OutwardRequest;
import com.paynetone.counter.model.response.GetOutwardResponse;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;
import com.paynetone.counter.utils.SharedPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class OutwardPresenter  extends Presenter<OutwardContract.View, OutwardContract.Interactor>
        implements OutwardContract.Presenter {

    Activity activity;
    SharedPref sharedPref;
    PaynetModel paynetModel;

    public OutwardPresenter(ContainerView containerView) {
        super(containerView);

        activity = (Activity) containerView;
    }

    @Override
    public void getOutward(int merchantID,String balanceType) {
        mView.showProgress();
        OutwardRequest request = new OutwardRequest(merchantID,balanceType);
        mInteractor.getOutward(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                if ("00".equals(response.body().getErrorCode())) {
                    List<GetOutwardResponse> outwardResponses = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<GetOutwardResponse>>() {
                    }.getType());
                    mView.showOutward(outwardResponses);
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
        sharedPref = new SharedPref(activity);
        paynetModel = sharedPref.getPaynet();
//        getOutward();
    }

    @Override
    public OutwardContract.Interactor onCreateInteractor() {
        return new OutwardInteractor(this);
    }

    @Override
    public OutwardContract.View onCreateView() {
        return OutwardFragment.getInstance();
    }
}
