package com.paynetone.counter.network;

import android.util.Log;

import com.blankj.utilcode.util.SpanUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paynetone.counter.BuildConfig;
import com.paynetone.counter.model.PostIdRequest;
import com.paynetone.counter.model.RequestObject;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.model.request.BaseRequest;
import com.paynetone.counter.model.request.ChangePassByOTPRequest;
import com.paynetone.counter.model.request.DashboardRequest;
import com.paynetone.counter.model.request.EmployeeAddNewRequest;
import com.paynetone.counter.model.request.LoginRequest;
import com.paynetone.counter.model.request.MerchantAddNewRequest;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.model.request.OrderGetByCodeRequest;
import com.paynetone.counter.model.request.OrderSearchRequest;
import com.paynetone.counter.model.request.OutwardRequest;
import com.paynetone.counter.model.request.PINAddRequest;
import com.paynetone.counter.model.request.PartNerAddressRequest;
import com.paynetone.counter.model.request.PaynetGetByParentRequest;
import com.paynetone.counter.model.request.RequestGetOtp;
import com.paynetone.counter.model.request.RequestOtp;
import com.paynetone.counter.model.request.RequestPayNetHasChildren;
import com.paynetone.counter.model.request.TopupAddressRequest;
import com.paynetone.counter.model.request.TranSearchRequest;
import com.paynetone.counter.model.request.UpdatePasswordRequest;
import com.paynetone.counter.model.request.WithdrawRequest;
import com.paynetone.counter.model.request.WithdrawSearchRequest;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.RSAUtil;
import com.paynetone.counter.utils.Utils;

import java.io.File;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkController {
    private static volatile PaynetOneApi apiBuilder;
    private static volatile PaynetOneApi apiBuilderImage;
    private static volatile PaynetOneApi apiBuilderRx;

    private NetWorkController() {

    }

    public static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    private static PaynetOneApi getAPIBuilder() {
        // mPrivateKeySignature = Constants.PRIVATE_KEY;
        // mApiKey = Constants.API_KEY;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(Utils.getUnsafeOkHttpClient(120, 120, "UGF5bmV0T25lOkdhdGV3YXlAMjEyMSFAIw=="))
                .build();
        apiBuilder = retrofit.create(PaynetOneApi.class);
        return apiBuilder;
    }

    private static PaynetOneApi getApiBuilderRx() {
        // mPrivateKeySignature = Constants.PRIVATE_KEY;
        // mApiKey = Constants.API_KEY;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(Utils.getUnsafeOkHttpClient(120, 120, "UGF5bmV0T25lOkdhdGV3YXlAMjEyMSFAIw=="))
                .build();
        apiBuilderRx = retrofit.create(PaynetOneApi.class);
        return apiBuilderRx;
    }

    private static PaynetOneApi getAPIBuilderImage() {
        if (apiBuilderImage == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.IMAGE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(Utils.getUnsafeOkHttpClient(120, 120,  "UGF5bmV0T25lOkRhdGFGaWxlQDIxMjEhQCM="))
                    .build();
            apiBuilderImage = retrofit.create(PaynetOneApi.class);
        }
        return apiBuilderImage;
    }

    public static void postImage(String filePath, CommonCallback<SimpleResult> callback) {
        try {
            File file = new File(filePath);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("pid_image", "file_pid_image.jpg", reqFile);
            Call<SimpleResult> call = getAPIBuilderImage().postImage(body);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void postImage(MultipartBody.Part body, CommonCallback<SimpleResult> callback){
        try {
            Call<SimpleResult> call = getAPIBuilderImage().postImage(body);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void login(LoginRequest loginRequest, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(loginRequest);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_EMP_LOGIN, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void orderAdd(OrderAddRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_ORDER_ADD_NEW, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getByOrderCode(String orderCode, CommonCallback<SimpleResult> callback) {
        try {
            OrderGetByCodeRequest request = new OrderGetByCodeRequest();
            request.setCode(orderCode);
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_ORDER_GET_BY_CODE, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void orderSearch(OrderSearchRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_ORDER_SEARCH, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Single<SimpleResult> orderSearch(OrderSearchRequest request) {
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_ORDER_SEARCH, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);
    }

    public static void getPaynet(BaseRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_PAYNET_GET_BY_ID, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getBalance(BaseRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_MERCHANT_GET_BALANCE, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Single<SimpleResult> getBalance(BaseRequest request){
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_MERCHANT_GET_BALANCE, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);

    }

    public static void getBank(CommonCallback<SimpleResult> callback) {
        try {
            String signature = RSAUtil.signature("");
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_DIC_GET_BANK, "", "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getWallet(CommonCallback<SimpleResult> callback){
        try {
            String signature = RSAUtil.signature("");
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_WALLET, "", "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getBusinessServices(CommonCallback<SimpleResult> callback) {
        try {
            String signature = RSAUtil.signature("");
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_DIC_BUSINESS_SERVICES, "", "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getProvinces(CommonCallback<SimpleResult> callback) {
        try {
            String signature = RSAUtil.signature("");
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_DIC_GET_PROVINCE, "", "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getParams(CommonCallback<SimpleResult> callback) {
        try {
            String signature = RSAUtil.signature("");
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_DIC_GET_PARAMS, "", "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getDistricts(int provinceID, CommonCallback<SimpleResult> callback) {
        try {
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setID(provinceID);
            String data = getGson().toJson(baseRequest);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_DIC_GET_DISTRICT, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getWards(int districtID, CommonCallback<SimpleResult> callback) {
        try {
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setID(districtID);
            String data = getGson().toJson(baseRequest);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_DIC_GET_WARD, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void withdraw(WithdrawRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_TRANS_WITHDRAW, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getOutward(OutwardRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_TRANS_GET_OUTWARD, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void withdrawSearch(WithdrawSearchRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_TRANS_WITHDRAW_SEARCH, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void employeeAdd(EmployeeAddNewRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_EMP_ADD_NEW, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getOTP(String mobile,String isForget, CommonCallback<SimpleResult> callback) {
        try {
            BaseRequest request = new BaseRequest();
            request.setMobileNumber(mobile);
            request.setIsForget(isForget);
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_EMP_GET_OTP, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Single<SimpleResult> getOTP(RequestGetOtp request){
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_EMP_GET_OTP, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);
    }
    public static void updatePassword(ChangePassByOTPRequest request, CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EMP_UPDATE_PASSWORD_BY_OTP, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addMerchant(MerchantAddNewRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_MERCHANT_ADD_NEW, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void editMerchant(MerchantAddNewRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_MERCHANT_EDIT, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getByMobileNumber(String mobile, CommonCallback<SimpleResult> callback) {
        try {
            BaseRequest request = new BaseRequest();
            request.setMobileNumber(mobile);
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.CMD_MERCHANT_GET_BY_MOBILE_NUMBER, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestOtp(RequestOtp request, CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EMP_OTP_VERIFY, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Single<SimpleResult> requestOtp(RequestOtp request){
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EMP_OTP_VERIFY, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);
    }


    public static void requestViTopupArress(TopupAddressRequest request, CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_VI_TOPUP_ADDRESS, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void requestVBookingArress(TopupAddressRequest request, CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_VBOOKING_ADDRESS, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void requestPartnerAddress(PartNerAddressRequest request,CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_PARTNER_ADDRESS, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void requestAddressPayNetID(BaseRequest request,CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_ADDRESS_BY_PAYNETID, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Single<SimpleResult> requestAddressPayNetID(BaseRequest request){
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_ADDRESS_BY_PAYNETID, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);
    }

    public static void requestBanner(CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson("");
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_APP_BANNER, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void requestPayNetHasChildren(RequestPayNetHasChildren request, CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PAYNET_CHECK_IF_HAS_CHILDREN, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void requestTransSearch(TranSearchRequest request, CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.TRANS_SEARCH, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Single<SimpleResult> requestTransSearch(TranSearchRequest request){
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.TRANS_SEARCH, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);

    }
    public static void requestProvider(CommonCallback<SimpleResult> callback){
        try {
            String signature = RSAUtil.signature("");
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_PROVIDERS, "", "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Single<SimpleResult> requestProvider(){
        String signature = RSAUtil.signature("");
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_PROVIDERS, "", "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);
    }
    public static void paynetGetByParentRequest(PaynetGetByParentRequest request,CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PAYNET_GET_BY_PARENT, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Single<SimpleResult> paynetGetByParentRequest(PaynetGetByParentRequest request){
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PAYNET_GET_BY_PARENT, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);
    }
    public static void paynetGetBalanceByID(BaseRequest request, CommonCallback<SimpleResult> callback) {
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PAYNET_GET_BALANCE_BY_ID, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Single<SimpleResult> paynetGetBalanceByID(BaseRequest request){
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PAYNET_GET_BALANCE_BY_ID, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);
    }
    public static void getPostId(PostIdRequest request,CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DIC_GET_POS, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Single<SimpleResult> requestAddPinCode(PINAddRequest request){
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EMP_PIN_ADD, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);
    }
    public static Single<SimpleResult> requestChangePassword(UpdatePasswordRequest request){
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EMP_UPDATE_PASSWORD, data, "", signature);
        return getApiBuilderRx().commonServiceRx(requestObject);
    }
    public static void requestVerifyPinCode(PINAddRequest request,CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EMP_PIN_VERIFY, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestPINCode(PINAddRequest request,CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EMP_PIN_HAS_OR_NOT, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void questDashboard(DashboardRequest request, CommonCallback<SimpleResult> callback){
        try {
            String data = getGson().toJson(request);
            String signature = RSAUtil.signature(data);
            RequestObject requestObject = new RequestObject("ANDROID", "", Constants.REPORT_MOBILE_DASHBOARD, data, "", signature);
            Call<SimpleResult> call = getAPIBuilder().commonService(requestObject);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
