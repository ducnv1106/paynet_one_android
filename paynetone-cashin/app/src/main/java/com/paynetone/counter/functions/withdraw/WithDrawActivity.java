package com.paynetone.counter.functions.withdraw;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.base.PaynetOneActivity;

public class WithDrawActivity extends PaynetOneActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        return (ViewFragment)new WithDrawPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
