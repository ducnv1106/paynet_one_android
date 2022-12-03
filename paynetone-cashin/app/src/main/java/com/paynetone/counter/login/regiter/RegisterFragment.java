package com.paynetone.counter.login.regiter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.R;
import com.paynetone.counter.adapter.StepperAdapter;
import com.paynetone.counter.interfaces.RegisterPassData;
import com.paynetone.counter.login.regiter.account.AccountPresenter;
import com.paynetone.counter.login.regiter.merchant.MerchantPresenter;
import com.paynetone.counter.model.RegisterPassDataModel;
import com.paynetone.counter.utils.Constants;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;

import butterknife.BindView;

public class RegisterFragment extends ViewFragment<RegisterContract.Presenter> implements RegisterContract.View, RegisterPassData,StepperLayout.StepperListener {
    @BindView(R.id.stepperLayout)
    StepperLayout mStepperLayout;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    String merchantName, mobileNumber, email;
    RegisterPassDataModel registerPassData;
    @BindView(R.id.btn_continue)
    AppCompatButton btnContinue;
    ArrayList<Fragment> listFragment = new ArrayList<Fragment>();
    StepperAdapter adapter;



    public static RegisterFragment getInstance() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        ivBack.setOnClickListener(view -> mPresenter.back());
        initPagerAdapter();

    }

    public void initPagerAdapter(){

        mStepperLayout.setListener(this);
        adapter = new StepperAdapter(requireActivity().getSupportFragmentManager(), requireActivity(),this);
        mStepperLayout.setAdapter(adapter);
        btnContinue.setOnClickListener(view -> {
            mStepperLayout.proceed();
//            mStepperLayout.setCurrentStepPosition(1);
        });
    }

    @Override
    public void saveData(RegisterPassDataModel dataModel) {
        this.registerPassData = dataModel;
    }

    @Override
    public RegisterPassDataModel getData() {
        return registerPassData;
    }

    @Override
    public void onCompleted(View completeButton) {

    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {
        try {
            if (newStepPosition==1){
                btnContinue.setText("Xác nhận");
            }else if (newStepPosition==2){
                btnContinue.setText("Hoàn tất");
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void onReturn() {

    }
}
