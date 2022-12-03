package com.paynetone.counter.functions.withdraw;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.BankModel;
import com.paynetone.counter.model.MerchantModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.PaynetGetByParentRequest;
import com.paynetone.counter.model.request.WithdrawRequest;
import com.paynetone.counter.model.response.PaynetGetByParentResponse;
import com.paynetone.counter.model.response.WalletResponse;
import com.paynetone.counter.network.CommonCallback;

import java.util.ArrayList;
import java.util.List;

public interface WithDrawContract {
    interface Interactor extends IInteractor<WithDrawContract.Presenter> {
        void getBank(CommonCallback<SimpleResult> callback);

        void getWallet(CommonCallback<SimpleResult> callback);

        void withdraw(WithdrawRequest withdrawRequest, CommonCallback<SimpleResult> callback);

        void getByMobileNumber(String mobileNumber, CommonCallback<SimpleResult> callback);

        void paynetGetByParentRequest(PaynetGetByParentRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<WithDrawContract.Presenter> {
        void showBanks(List<BankModel> bankModels);

        void showSuccess(String retRefNumber);

        void showListWallet(List<WalletResponse> walletResponseList);

        void showMerchant(MerchantModel model);

        void showPaynetGetByParentRequest(ArrayList<PaynetGetByParentResponse> responses);
    }

    interface Presenter extends IPresenter<WithDrawContract.View, WithDrawContract.Interactor> {
        void withdraw(WithdrawRequest withdrawRequest);
        void getWallet();
        void getByMobileNumber(String mobileNumber);
        void paynetGetByParentRequest(int parentID);
    }
}
