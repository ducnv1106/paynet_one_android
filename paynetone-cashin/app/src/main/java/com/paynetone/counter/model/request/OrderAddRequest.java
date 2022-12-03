package com.paynetone.counter.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderAddRequest {
    @SerializedName("ProviderCode")
    @Expose
    private String providerCode;
    @SerializedName("EmpID")
    @Expose
    private int empID;
    @SerializedName("PaynetID")
    @Expose
    private int paynetID;
    @SerializedName("TransCategory")
    @Expose
    private int transCategory;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("PaymentType")
    @Expose
    private int paymentType;
    @SerializedName("PaymentCate")
    @Expose
    private int paymentCate;
    @SerializedName("ServiceID")
    @Expose
    private int serviceID;
    @SerializedName("ProductID")
    @Expose
    private int productID;
    @SerializedName("Quantity")
    @Expose
    private int quantity;
    @SerializedName("Amount")
    @Expose
    private int amount;
    @SerializedName("Fee")
    @Expose
    private int fee;
    @SerializedName("TransAmount")
    @Expose
    private int transAmount;
    @SerializedName("OrderDes")
    @Expose
    private String orderDes;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("PIDNumber")
    @Expose
    private String pIDNumber;
    @SerializedName("OffLine")
    @Expose
    private String offLine;
    @SerializedName("InstaType")
    @Expose
    private String instaType;
    @SerializedName("InstallmentMonth")
    @Expose
    private int installmentMonth;
    @SerializedName("PrepaidAmount")
    @Expose
    private int prepaidAmount;
    @SerializedName("PrepaidPercent")
    @Expose
    private int prepaidPercent;
    @SerializedName("InstallmentAmount")
    @Expose
    private int installmentAmount;
    @SerializedName("MerchantFee")
    @Expose
    private int merchantFee;
    @SerializedName("MonthlyAmount")
    @Expose
    private int monthlyAmount;
    @SerializedName("OrderCode")
    @Expose
    private String orderCode;
    @SerializedName("Channel")
    @Expose
    private String channel;
    @SerializedName("MerchantID")
    @Expose
    private int merchantID;
    @SerializedName("ProviderAcntID")
    @Expose
    private int providerAcntID;

    public int getProviderAcntID() {
        return providerAcntID;
    }

    public void setProviderAcntID(int providerAcntID) {
        this.providerAcntID = providerAcntID;
    }

    public int getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(int merchantID) {
        this.merchantID = merchantID;
    }

    public int getPaymentCate() {
        return paymentCate;
    }

    public void setPaymentCate(int paymentCate) {
        this.paymentCate = paymentCate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getpIDNumber() {
        return pIDNumber;
    }

    public void setpIDNumber(String pIDNumber) {
        this.pIDNumber = pIDNumber;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getPaynetID() {
        return paynetID;
    }

    public void setPaynetID(int paynetID) {
        this.paynetID = paynetID;
    }

    public int getTransCategory() {
        return transCategory;
    }

    public void setTransCategory(int transCategory) {
        this.transCategory = transCategory;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(int transAmount) {
        this.transAmount = transAmount;
    }

    public String getOrderDes() {
        return orderDes;
    }

    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPIDNumber() {
        return pIDNumber;
    }

    public void setPIDNumber(String pIDNumber) {
        this.pIDNumber = pIDNumber;
    }

    public String getOffLine() {
        return offLine;
    }

    public void setOffLine(String offLine) {
        this.offLine = offLine;
    }

    public String getInstaType() {
        return instaType;
    }

    public void setInstaType(String instaType) {
        this.instaType = instaType;
    }

    public int getInstallmentMonth() {
        return installmentMonth;
    }

    public void setInstallmentMonth(int installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public int getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(int prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public int getPrepaidPercent() {
        return prepaidPercent;
    }

    public void setPrepaidPercent(int prepaidPercent) {
        this.prepaidPercent = prepaidPercent;
    }

    public int getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(int installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public int getMerchantFee() {
        return merchantFee;
    }

    public void setMerchantFee(int merchantFee) {
        this.merchantFee = merchantFee;
    }

    public int getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(int monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
