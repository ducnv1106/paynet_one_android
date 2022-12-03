package com.paynetone.counter.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawSearchResponse {
    @SerializedName("TransDate")
    @Expose
    private String transDate;
    @SerializedName("RetRefNumber")
    @Expose
    private String retRefNumber;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("Fee")
    @Expose
    private Integer fee;
    @SerializedName("TransAmount")
    @Expose
    private Integer transAmount;
    @SerializedName("TransReason")
    @Expose
    private String transReason;
    @SerializedName("ReturnCode")
    @Expose
    private int returnCode;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("AccountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("BankShortName")
    @Expose
    private String bankShortName;
    @SerializedName("BankName")
    @Expose
    private String bankName;
    @SerializedName("MerchantCode")
    @Expose
    private String merchantCode;
    @SerializedName("MerchantName")
    @Expose
    private String merchantName;

    @SerializedName("WalletCode")
    @Expose
    private String walletCode;
    @SerializedName("WalletName")
    @Expose
    private String walletName;
    @SerializedName("WithDrawCatefory")
    @Expose
    private int withDrawCatefory;
    @SerializedName("ShopName")
    @Expose
    private String shopName;

    @SerializedName("ShopCode")
    @Expose
    private String shopCode;

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getWalletCode() {
        return walletCode;
    }

    public void setWalletCode(String walletCode) {
        this.walletCode = walletCode;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public int getWithDrawCatefory() {
        return withDrawCatefory;
    }

    public void setWithDrawCatefory(int withDrawCatefory) {
        this.withDrawCatefory = withDrawCatefory;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getRetRefNumber() {
        return retRefNumber;
    }

    public void setRetRefNumber(String retRefNumber) {
        this.retRefNumber = retRefNumber;
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

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
