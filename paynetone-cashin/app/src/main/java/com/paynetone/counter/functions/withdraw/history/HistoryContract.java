package com.paynetone.counter.functions.withdraw.history;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.WithdrawSearchRequest;
import com.paynetone.counter.model.response.WithdrawSearchResponse;
import com.paynetone.counter.network.CommonCallback;

import java.util.List;

public interface HistoryContract {
    interface Interactor extends IInteractor<HistoryContract.Presenter> {
        void withdrawSearch(WithdrawSearchRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<HistoryContract.Presenter> {
        void showWithdraws(List<WithdrawSearchResponse> withdrawSearchResponses);
    }

    interface Presenter extends IPresenter<HistoryContract.View, HistoryContract.Interactor> {
        void withdrawSearch();
    }
}
