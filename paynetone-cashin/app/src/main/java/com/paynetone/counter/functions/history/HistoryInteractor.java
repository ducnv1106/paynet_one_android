package com.paynetone.counter.functions.history;

import com.core.base.viper.Interactor;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.OrderSearchRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

public class HistoryInteractor  extends Interactor<HistoryContract.Presenter>
        implements HistoryContract.Interactor{

    public HistoryInteractor(HistoryContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void orderSearch(OrderSearchRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.orderSearch(request, callback);
    }
}
