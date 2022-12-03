package com.paynetone.counter.login.regiter.account;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.RegisterPassDataModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.EmployeeAddNewRequest;
import com.paynetone.counter.model.request.LoginRequest;
import com.paynetone.counter.network.CommonCallback;

public interface AccountContract {
    interface Interactor extends IInteractor<AccountContract.Presenter> {
        void employeeAdd(EmployeeAddNewRequest request, CommonCallback<SimpleResult> callback);

        void getOTP(String mobile,String isForget, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<AccountContract.Presenter> {
        void showSuccess();

        void showError(String message);
        void showSuccessOTP();
    }

    interface Presenter extends IPresenter<AccountContract.View, AccountContract.Interactor> {
        void employeeAdd(EmployeeAddNewRequest request);

        void getOTP(String mobile,String isForget);

        void saveData(RegisterPassDataModel dataModel);
    }
}
