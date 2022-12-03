package com.paynetone.counter.functions.qr;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.base.PaynetOneActivity;
import com.paynetone.counter.model.PaymentModel;
import com.paynetone.counter.model.request.GetProviderResponse;
import com.paynetone.counter.utils.ExtraConst;

public class QRDynamicActivity extends PaynetOneActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        Intent intent = getIntent();
        GetProviderResponse providerResponse = intent.getParcelableExtra(ExtraConst.EXTRA_PROVIDER_RESPONSE);
        return (ViewFragment)new QRDynamicPresenter((ContainerView)getBaseActivity(),providerResponse).getFragment();
    }
}
