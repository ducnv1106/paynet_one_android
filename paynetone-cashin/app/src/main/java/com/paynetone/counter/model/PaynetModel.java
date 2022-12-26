package com.paynetone.counter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaynetModel {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("PnoLevel")
    @Expose
    private String pnoLevel;
    @SerializedName("ParentID")
    @Expose
    private Integer parentID;
    @SerializedName("MerchantID")
    @Expose
    private Integer merchantID;
    @SerializedName("BusinessType")
    @Expose
    private Integer businessType;
    @SerializedName("PosID")
    @Expose
    private String posID;
    @SerializedName("MerchantCode")
    private String merchantCode;

    @SerializedName("MerchantStatus")
    @Expose
    private String merchantStatus;


    public String getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(String merchantStatus) {
        this.merchantStatus = merchantStatus;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getPosID() {
        return posID;
    }

    public void setPosID(String posID) {
        this.posID = posID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPnoLevel() {
        return pnoLevel;
    }

    public void setPnoLevel(String pnoLevel) {
        this.pnoLevel = pnoLevel;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public Integer getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(Integer merchantID) {
        this.merchantID = merchantID;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }
}
