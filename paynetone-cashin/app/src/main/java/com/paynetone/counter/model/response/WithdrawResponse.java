package com.paynetone.counter.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawResponse {
    @SerializedName("RetRefNumber")
    @Expose
    private String retRefNumber;

    public String getRetRefNumber() {
        return retRefNumber;
    }

    public void setRetRefNumber(String retRefNumber) {
        this.retRefNumber = retRefNumber;
    }
}
