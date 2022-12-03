package com.paynetone.counter.network;

import com.paynetone.counter.model.RequestObject;
import com.paynetone.counter.model.SimpleResult;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PaynetOneApi {
    @POST("Gateway/Execute")
    Call<SimpleResult> commonService(@Body RequestObject requestObject);

    @POST("Gateway/Execute")
    Single<SimpleResult> commonServiceRx(@Body RequestObject requestObject);

    @Multipart
    @POST("Handle/UploadImage")
    Call<SimpleResult> postImage(@Part MultipartBody.Part body);
}
