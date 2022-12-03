package com.paynetone.counter.personal;

import com.core.base.viper.Interactor;
import com.paynetone.counter.main.MainContract;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.BaseRequest;
import com.paynetone.counter.model.request.PINAddRequest;
import com.paynetone.counter.model.request.TranSearchRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

public class PersonalInteractor extends Interactor<PersonalContract.Presenter>
        implements PersonalContract.Interactor {

    public PersonalInteractor(PersonalContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getBalance(BaseRequest baseRequest, CommonCallback<SimpleResult> callback) {
        NetWorkController.getBalance(baseRequest, callback);
    }

    @Override
    public void paynetGetBalanceByID(BaseRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.paynetGetBalanceByID(request,callback);
    }

    @Override
    public void requestPINCode(PINAddRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.requestPINCode(request,callback);
    }
}
