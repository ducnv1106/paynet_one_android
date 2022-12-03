package com.paynetone.counter.login.regiter.merchant;

import com.core.base.viper.Interactor;
import com.paynetone.counter.model.PostIdRequest;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.MerchantAddNewRequest;
import com.paynetone.counter.network.CommonCallback;
import com.paynetone.counter.network.NetWorkController;

import okhttp3.MultipartBody;

public class MerchantInteractor extends Interactor<MerchantContract.Presenter>
        implements MerchantContract.Interactor {

    public MerchantInteractor(MerchantContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void getBusinessServices(CommonCallback<SimpleResult> callback) {
        NetWorkController.getBusinessServices(callback);
    }

    @Override
    public void getProvinces(CommonCallback<SimpleResult> callback) {
        NetWorkController.getProvinces(callback);
    }

    @Override
    public void getBanks(CommonCallback<SimpleResult> callback) {
        NetWorkController.getBank(callback);
    }

    @Override
    public void getDistricts(int provinceID, CommonCallback<SimpleResult> callback) {
        NetWorkController.getDistricts(provinceID, callback);
    }

    @Override
    public void getWards(int districtID, CommonCallback<SimpleResult> callback) {
        NetWorkController.getWards(districtID, callback);
    }

    @Override
    public void postImage(String filePath, CommonCallback<SimpleResult> callback) {
        NetWorkController.postImage(filePath, callback);
    }

    @Override
    public void postImage(MultipartBody.Part body, CommonCallback<SimpleResult> callback) {
        NetWorkController.postImage(body,callback);
    }

    @Override
    public void addMerchant(MerchantAddNewRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.addMerchant(request, callback);
    }

    @Override
    public void editMerchant(MerchantAddNewRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.editMerchant(request, callback);
    }

    @Override
    public void getByMobileNumber(String mobileNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.getByMobileNumber(mobileNumber, callback);
    }

    @Override
    public void getPostID(PostIdRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.getPostId(request, callback);
    }
}
