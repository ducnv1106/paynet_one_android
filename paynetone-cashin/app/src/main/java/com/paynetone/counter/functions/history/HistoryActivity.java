package com.paynetone.counter.functions.history;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.base.PaynetOneActivity;

public class HistoryActivity  extends PaynetOneActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new HistoryPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
