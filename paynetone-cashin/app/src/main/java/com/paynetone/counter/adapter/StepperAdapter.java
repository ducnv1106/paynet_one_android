package com.paynetone.counter.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.R;
import com.paynetone.counter.interfaces.RegisterPassData;
import com.paynetone.counter.login.regiter.account.AccountPresenter;
import com.paynetone.counter.login.regiter.merchant.MerchantPresenter;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.view.StepFragmentSample;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.ArrayList;

public class StepperAdapter extends AbstractFragmentStepAdapter {
    Context mContext;
    RegisterPassData registerPassData;

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context, RegisterPassData registerPassData) {
        super(fm, context);

        this.mContext = context;
        this.registerPassData = registerPassData;
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 0:
                return (Step) new AccountPresenter((ContainerView) mContext, registerPassData).getFragment();
            case 1:
                return (Step) new MerchantPresenter((ContainerView) mContext, Constants.MERCHANT_MODE_ADD, registerPassData).getFragment();
            default:
                return new StepFragmentSample();
        }
    }


    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        StepViewModel.Builder builder = new StepViewModel.Builder(context);
        builder.setBackButtonVisible(false);
        return builder.create();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
