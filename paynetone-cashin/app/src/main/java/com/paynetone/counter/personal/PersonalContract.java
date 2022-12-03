package com.paynetone.counter.personal;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.MerchantBalance;
import com.paynetone.counter.model.PaynetGetBalanceByIdResponse;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.BaseRequest;
import com.paynetone.counter.model.request.PINAddRequest;
import com.paynetone.counter.model.request.TranSearchRequest;
import com.paynetone.counter.model.response.PINCodeResponse;
import com.paynetone.counter.model.response.TranSearchResponse;
import com.paynetone.counter.network.CommonCallback;

import java.util.ArrayList;
import java.util.List;

public interface PersonalContract {
    interface Interactor extends IInteractor<PersonalContract.Presenter> {
        void getBalance(BaseRequest baseRequest, CommonCallback<SimpleResult> callback);
        void paynetGetBalanceByID(BaseRequest request, CommonCallback<SimpleResult> callback);
        void requestPINCode(PINAddRequest request, CommonCallback<SimpleResult>callback);

    }

    interface View extends PresentView<PersonalContract.Presenter> {
        void showBalance(List<MerchantBalance> merchantBalances);
        void showManagerHanMuc(ArrayList<PaynetGetBalanceByIdResponse> responses);
        void showPINCodeSuccess(PINCodeResponse response);
    }

    interface Presenter extends IPresenter<PersonalContract.View, PersonalContract.Interactor> {
        void getBalance(int merchantID);
        void paynetGetBalanceByID(int requestID);
        void requestPINCode(PINAddRequest request);
    }
}
