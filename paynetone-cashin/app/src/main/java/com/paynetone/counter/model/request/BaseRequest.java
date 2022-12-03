package com.paynetone.counter.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseRequest {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("MerchantID")
    @Expose
    private int merchantID;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;

    @SerializedName("IsForget")
    @Expose
    private String isForget;
    @SerializedName("PaynetID")
    @Expose
    private int paynetID ;

    public int getPaynetID() {
        return paynetID;
    }

    public void setPaynetID(int paynetID) {
        this.paynetID = paynetID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getIsForget(){
        return isForget;
    }
    public void setIsForget(String isForget){
        this.isForget=isForget;
    }

    public int getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(int merchantID) {
        this.merchantID = merchantID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
