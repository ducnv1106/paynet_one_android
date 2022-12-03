package com.paynetone.counter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeModel {
    @SerializedName("PaynetID")
    @Expose
    private Integer paynetID;
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("RoleID")
    @Expose
    private Integer roleID;
    @SerializedName("BankID")
    @Expose
    private Integer bankID;

    @SerializedName("BankName")
    @Expose
    private String bankName;

    @SerializedName("PaymentAccNo")
    @Expose
    private String paymentAccNo;
    @SerializedName("PaymentAccName")
    @Expose
    private String paymentAccName;

    @SerializedName("Pin")
    @Expose
    private String pin;

    @SerializedName("IDMerAdmin")
    private int iDMerAdmin;

    public int getiDMerAdmin() {
        return iDMerAdmin;
    }

    public void setiDMerAdmin(int iDMerAdmin) {
        this.iDMerAdmin = iDMerAdmin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankID(Integer bankID) {
        this.bankID = bankID;
    }

    public void setPaymentAccNo(String paymentAccNo) {
        this.paymentAccNo = paymentAccNo;
    }

    public void setPaymentAccName(String paymentAccName) {
        this.paymentAccName = paymentAccName;
    }

    public Integer getBankID() {
        return bankID;
    }

    public String getPaymentAccNo() {
        return paymentAccNo;
    }

    public String getPaymentAccName() {
        return paymentAccName;
    }

    public Integer getPaynetID() {
        return paynetID;
    }

    public void setPaynetID(Integer paynetID) {
        this.paynetID = paynetID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }
}
