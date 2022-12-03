package com.paynetone.counter.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeAddNewRequest {
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("IsRegister")
    @Expose
    private String isRegister;
    @SerializedName("PaynetID")
    @Expose
    private Integer paynetID;
    @SerializedName("RoleID")
    @Expose
    private Integer roleID = 1;
    @SerializedName("EmpID")
    @Expose
    private Integer empID;
    @SerializedName("OTP")
    @Expose
    private String otp;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(String isRegister) {
        this.isRegister = isRegister;
    }

    public Integer getPaynetID() {
        return paynetID;
    }

    public void setPaynetID(Integer paynetID) {
        this.paynetID = paynetID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
