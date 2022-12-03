package com.paynetone.counter.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;

    @SerializedName("Password")
    @Expose
    private String password;

    @SerializedName("FCMToken")
    @Expose
    private String token;

    public LoginRequest(String mobileNumber, String password, String token) {
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.token = token;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return token;
    }
}
