package com.paynetone.counter.login.regiter.account;

import com.core.base.viper.Interactor;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.EmployeeAddNewRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

public class AccountInteractor extends Interactor<AccountContract.Presenter>
        implements AccountContract.Interactor {

    public AccountInteractor(AccountContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void employeeAdd(EmployeeAddNewRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.employeeAdd(request, callback);
    }

    @Override
    public void getOTP(String mobile,String isForget, CommonCallback<SimpleResult> callback) {
        NetWorkController.getOTP(mobile,isForget, callback);
    }
}
