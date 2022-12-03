package com.paynetone.counter.login;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.base.PaynetOneActivity;

public class LoginActivity extends PaynetOneActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new LoginPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
