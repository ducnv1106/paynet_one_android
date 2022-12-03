package com.paynetone.counter.functions.outward;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.base.PaynetOneActivity;
public class OutwardActivity extends PaynetOneActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new OutwardPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
