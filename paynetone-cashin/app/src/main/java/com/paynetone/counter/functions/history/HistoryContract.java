package com.paynetone.counter.functions.history;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.model.request.OrderSearchRequest;
import com.paynetone.counter.network.CommonCallback;

import java.util.List;

public interface HistoryContract {
    interface Interactor extends IInteractor<Presenter> {
        void orderSearch(OrderSearchRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showOrders(List<OrderModel> historyModels);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void orderSearch();
        void orderSearchFilter(Integer branchID, Integer storeID,Integer stallID);
    }

}
