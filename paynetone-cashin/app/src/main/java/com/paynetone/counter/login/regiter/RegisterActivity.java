package com.paynetone.counter.login.regiter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.R;
import com.paynetone.counter.adapter.StepperAdapter;
import com.paynetone.counter.base.PaynetOneActivity;
import com.paynetone.counter.main.MainPresenter;
import com.stepstone.stepper.StepperLayout;

public class RegisterActivity  extends PaynetOneActivity {


    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new RegisterPresenter((ContainerView)getBaseActivity()).getFragment();
    }
}
