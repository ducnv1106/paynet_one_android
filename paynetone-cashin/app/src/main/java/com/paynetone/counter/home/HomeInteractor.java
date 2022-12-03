package com.paynetone.counter.home;

import com.core.base.viper.Interactor;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.BaseRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

public class HomeInteractor extends Interactor<HomeContract.Presenter>
        implements HomeContract.Interactor  {

    public HomeInteractor(HomeContract.Presenter presenter) {
        super(presenter);
    }

}
