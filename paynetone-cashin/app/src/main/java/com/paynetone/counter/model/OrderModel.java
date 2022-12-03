package com.paynetone.counter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderModel {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("EmpID")
    @Expose
    private Integer empID;
    @SerializedName("PaynetID")
    @Expose
    private Integer paynetID;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("TransCategory")
    @Expose
    private Integer transCategory;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("RetRefNumber")
    @Expose
    private String retRefNumber;
    @SerializedName("PaymentType")
    @Expose
    private Integer paymentType;
    @SerializedName("ServiceID")
    @Expose
    private Integer serviceID;
    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("Fee")
    @Expose
    private Integer fee;
    @SerializedName("TransAmount")
    @Expose
    private Integer transAmount;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("OrderDes")
    @Expose
    private String orderDes;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("PIDNumber")
    @Expose
    private String pIDNumber;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("LastChangeDate")
    @Expose
    private String lastChangeDate;
    @SerializedName("OffLine")
    @Expose
    private String offLine;
    @SerializedName("InstaType")
    @Expose
    private String instaType;
    @SerializedName("InstallmentMonth")
    @Expose
    private Integer installmentMonth;
    @SerializedName("PrepaidAmount")
    @Expose
    private Integer prepaidAmount;
    @SerializedName("PrepaidPercent")
    @Expose
    private Integer prepaidPercent;
    @SerializedName("InstallmentAmount")
    @Expose
    private Integer installmentAmount;
    @SerializedName("MerchantFee")
    @Expose
    private Integer merchantFee;
    @SerializedName("MonthlyAmount")
    @Expose
    private Integer monthlyAmount;
    @SerializedName("PartnerCode")
    @Expose
    private String partnerCode;
    @SerializedName("ProviderCode")
    @Expose
    private String providerCode;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public Integer getPaynetID() {
        return paynetID;
    }

    public void setPaynetID(Integer paynetID) {
        this.paynetID = paynetID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTransCategory() {
        return transCategory;
    }

    public void setTransCategory(Integer transCategory) {
        this.transCategory = transCategory;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRetRefNumber() {
        return retRefNumber;
    }

    public void setRetRefNumber(String retRefNumber) {
        this.retRefNumber = retRefNumber;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(String lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
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

    public Integer getInstallmentMonth() {
        return installmentMonth;
    }

    public void setInstallmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public Integer getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(Integer prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public Integer getPrepaidPercent() {
        return prepaidPercent;
    }

    public void setPrepaidPercent(Integer prepaidPercent) {
        this.prepaidPercent = prepaidPercent;
    }

    public Integer getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Integer installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Integer getMerchantFee() {
        return merchantFee;
    }

    public void setMerchantFee(Integer merchantFee) {
        this.merchantFee = merchantFee;
    }

    public Integer getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(Integer monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
}
