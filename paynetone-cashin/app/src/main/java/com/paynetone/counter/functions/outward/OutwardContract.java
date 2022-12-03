package com.paynetone.counter.functions.outward;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.OutwardRequest;
import com.paynetone.counter.model.response.GetOutwardResponse;
import com.paynetone.counter.network.CommonCallback;

import java.util.List;

public interface OutwardContract {
    interface Interactor extends IInteractor<OutwardContract.Presenter> {
        void getOutward(OutwardRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<OutwardContract.Presenter> {
        void showOutward(List<GetOutwardResponse> outwards);
    }

    interface Presenter extends IPresenter<OutwardContract.View, OutwardContract.Interactor> {
        void getOutward(int merchantID,String balanceType);
    }
}
