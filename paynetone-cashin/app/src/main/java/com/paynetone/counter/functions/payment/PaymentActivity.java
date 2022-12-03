package com.paynetone.counter.functions.payment;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.base.PaynetOneActivity;

public class PaymentActivity extends PaynetOneActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return null;// (ViewFragment)new PaymentPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
