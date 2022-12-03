package com.paynetone.counter.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOutwardResponse {
    @SerializedName("RetRefNumber")
    @Expose
    private String retRefNumber;
    @SerializedName("TransAmount")
    @Expose
    private Integer transAmount;
    @SerializedName("TransReason")
    @Expose
    private String transReason;
    @SerializedName("OrderCode")
    @Expose
    private String orderCode;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("Fee")
    @Expose
    private Integer fee;
    @SerializedName("TransDate")
    @Expose
    private String transDate;
    @SerializedName("ProviderCode")
    @Expose
    private String providerCode;
    @SerializedName("ProviderAcntName")
    @Expose
    private String providerAcntName;

    @SerializedName("MerchantAmount")
    @Expose
    private Integer merchantAmount;

    public Integer getMerchantAmount() {
        return merchantAmount;
    }

    public void setMerchantAmount(Integer merchantAmount) {
        this.merchantAmount = merchantAmount;
    }

    public String getProviderAcntName() {
        return providerAcntName;
    }

    public void setProviderAcntName(String providerAcntName) {
        this.providerAcntName = providerAcntName;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }
    public String getRetRefNumber() {
        return retRefNumber;
    }

    public void setRetRefNumber(String retRefNumber) {
        this.retRefNumber = retRefNumber;
    }

    public Integer getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Integer transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransReason() {
        return transReason;
    }

    public void setTransReason(String transReason) {
        this.transReason = transReason;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }
}
