package com.paynetone.counter.functions.history;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.google.gson.reflect.TypeToken;
import com.paynetone.counter.model.EmployeeModel;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.OrderSearchRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;
import com.paynetone.counter.utils.SharedPref;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HistoryPresenter extends Presenter<HistoryContract.View, HistoryContract.Interactor>
        implements HistoryContract.Presenter {

    SharedPref sharedPref;
    EmployeeModel employeeModel;

    public HistoryPresenter(ContainerView containerView) {
        super(containerView);
    }


    @Override
    public void start() {
        sharedPref = new SharedPref(getViewContext());
        employeeModel = sharedPref.getEmployeeModel();
    }

    @Override
    public void orderSearch() {
        try{
            mView.showProgress();
            OrderSearchRequest request = new OrderSearchRequest();
            request.setEmpID(employeeModel.getId());
            request.setPaynetID(employeeModel.getPaynetID());

            mInteractor.orderSearch(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);

                    if ("00".equals(response.body().getErrorCode())) {
                        List<OrderModel> order = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<OrderModel>>() {
                        }.getType());
                        mView.showOrders(order);
                    } else {
                        mView.showAlertDialog(response.body().getMessage());
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
    public void orderSearchFilter(Integer branchID, Integer storeID,Integer stallID) {
        try{
            mView.showProgress();
            OrderSearchRequest request = new OrderSearchRequest();
            request.setPaynetID(employeeModel.getPaynetID());

            if (branchID != null) request.setPaynetID(branchID);
            if (storeID != null) request.setPaynetID(storeID);
            if (stallID != null) request.setPaynetID(stallID);

            mInteractor.orderSearch(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);

                    if ("00".equals(response.body().getErrorCode())) {
                        List<OrderModel> order = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<OrderModel>>() {
                        }.getType());
                        mView.showOrders(order);
                    } else {
                        mView.showAlertDialog(response.body().getMessage());
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
    public HistoryContract.Interactor onCreateInteractor() {
        return new HistoryInteractor(this);
    }

    @Override
    public HistoryContract.View onCreateView() {
        return HistoryFragment.getInstance();
    }
}
