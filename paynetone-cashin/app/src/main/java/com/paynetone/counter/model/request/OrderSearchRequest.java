package com.paynetone.counter.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderSearchRequest {
    @SerializedName("EmpID")
    @Expose
    private Integer empID;
    @SerializedName("FromDate")
    @Expose
    private Object fromDate;
    @SerializedName("ToDate")
    @Expose
    private Object toDate;
    @SerializedName("Code")
    @Expose
    private Object code;
    @SerializedName("PartnerCode")
    @Expose
    private Object partnerCode;
    @SerializedName("MobileNumber")
    @Expose
    private Object mobileNumber;
    @SerializedName("PaynetID")
    @Expose
    private int paynetID ;
    @SerializedName("BranchCode")
    @Expose
    private String branchCode ;
    @SerializedName("ShopCode")
    @Expose
    private String shopCode ;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public int getPaynetID() {
        return paynetID;
    }

    public void setPaynetID(int paynetID) {
        this.paynetID = paynetID;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public void setFromDate(Object fromDate) {
        this.fromDate = fromDate;
    }

    public Object getToDate() {
        return toDate;
    }

    public void setToDate(Object toDate) {
        this.toDate = toDate;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(Object partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Object getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Object mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
