package com.paynetone.counter.login.regiter.merchant;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.paynetone.counter.model.BankModel;
import com.paynetone.counter.model.DictionaryModel;
import com.paynetone.counter.model.MerchantModel;
import com.paynetone.counter.model.PostIdRequest;
import com.paynetone.counter.model.RegisterPassDataModel;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.MerchantAddNewRequest;
import com.paynetone.counter.model.response.DictionaryBusinessServiceResponse;
import com.paynetone.counter.network.CommonCallback;

import java.util.List;

import okhttp3.MultipartBody;

public interface MerchantContract {
    interface Interactor extends IInteractor<MerchantContract.Presenter> {
        void getBusinessServices(CommonCallback<SimpleResult> callback);

        void getProvinces(CommonCallback<SimpleResult> callback);

        void getBanks(CommonCallback<SimpleResult> callback);

        void getDistricts(int provinceID, CommonCallback<SimpleResult> callback);

        void getWards(int districtID, CommonCallback<SimpleResult> callback);

        void postImage(String filePath, CommonCallback<SimpleResult> callback);

        void postImage(MultipartBody.Part body,CommonCallback<SimpleResult> callback);

        void addMerchant(MerchantAddNewRequest request, CommonCallback<SimpleResult> callback);

        void editMerchant(MerchantAddNewRequest request, CommonCallback<SimpleResult> callback);

        void getByMobileNumber(String mobileNumber, CommonCallback<SimpleResult> callback);

        void getPostID(PostIdRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<MerchantContract.Presenter> {
        void showBusinessService(List<DictionaryBusinessServiceResponse> models);

        void showProvinces(List<DictionaryModel> dictionaryModels);

        void showDistricts(List<DictionaryModel> dictionaryModels);

        void showWards(List<DictionaryModel> dictionaryModels);

        void showBanks(List<BankModel> bankModels);

        void showImage(String file);

        void handlerEditMerchantSuccess();

        void goToNextStep();

        void showError(String message);

        void showMerchant(MerchantModel model);

        void gotoSplashWhenUpdateMerchant();
        void showPostId(int postId);
    }

    interface Presenter extends IPresenter<MerchantContract.View, MerchantContract.Interactor> {
        String getMode();

        RegisterPassDataModel getPassData();

        void getDistricts(int provinceID);

        void getWards(int districtID);

        void postImage(String filePath);

        void postImage(MultipartBody.Part body);

        void addMerchant(MerchantAddNewRequest request);

        void editMerchant(MerchantAddNewRequest request);

        void getPostId(int id);
    }
}
