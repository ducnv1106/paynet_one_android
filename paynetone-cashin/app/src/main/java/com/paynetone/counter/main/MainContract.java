package com.paynetone.counter.main;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.MerchantBalance;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.BaseRequest;
import com.paynetone.counter.model.request.GetProviderResponse;
import com.paynetone.counter.model.request.RequestPayNetHasChildren;
import com.paynetone.counter.model.response.BannerResponse;
import com.paynetone.counter.network.CommonCallback;

import java.util.ArrayList;
import java.util.List;

public interface MainContract {
    interface Interactor extends IInteractor<Presenter> {
        void getBalance(BaseRequest baseRequest, CommonCallback<SimpleResult> callback);
        void requestProvider(CommonCallback<SimpleResult> callback);
        void requestAddressPayNetID(BaseRequest request,CommonCallback<SimpleResult> callback);
        void requestBanner(CommonCallback<SimpleResult> callback);
        void requestPayNetHasChildren(RequestPayNetHasChildren request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showBalance(List<MerchantBalance> merchantBalances);
        void showSuccessProvider(ArrayList<GetProviderResponse> responses);
        void showBanner(ArrayList<BannerResponse> banners);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getBalance();
        void requestProvider();
        void requestAddressPayNetID();
        void requestBanner();
        void requestPayNetHasChildren();
    }
}
