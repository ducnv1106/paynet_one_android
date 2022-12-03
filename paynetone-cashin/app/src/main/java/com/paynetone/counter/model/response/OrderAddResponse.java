package com.paynetone.counter.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderAddResponse {
    @SerializedName("ReturnURL")
    @Expose
    private String returnURL;
    @SerializedName("OrderCode")
    @Expose
    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getReturnURL() {
        return returnURL;
    }

    public void setReturnURL(String returnURL) {
        this.returnURL = returnURL;
    }
}
