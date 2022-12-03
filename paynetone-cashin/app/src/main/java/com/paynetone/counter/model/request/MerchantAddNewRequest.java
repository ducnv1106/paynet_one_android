package com.paynetone.counter.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MerchantAddNewRequest {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("BusinessType")
    @Expose
    private String businessType;
    @SerializedName("BusinessMobile")
    @Expose
    private String businessMobile;
    @SerializedName("FormalityType")
    @Expose
    private String formalityType;
    @SerializedName("TaxCode")
    @Expose
    private String taxCode;
    @SerializedName("Fax")
    @Expose
    private String fax;
    @SerializedName("ContractCode")
    @Expose
    private String contractCode;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("PrintQRName")
    @Expose
    private String printQRName;
    @SerializedName("ServiceType")
    @Expose
    private String serviceType;
    @SerializedName("ProvinceID")
    @Expose
    private Integer provinceID;
    @SerializedName("DistrictID")
    @Expose
    private Integer districtID;
    @SerializedName("WardID")
    @Expose
    private Integer wardID;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("RepresentativeName")
    @Expose
    private String representativeName;
    @SerializedName("RepresentativeMobile")
    @Expose
    private String representativeMobile;
    @SerializedName("RepresentativePosition")
    @Expose
    private String representativePosition;
    @SerializedName("RepresentativePIDNumber")
    @Expose
    private String representativePIDNumber;
    @SerializedName("RepresentativePIDImageBefore")
    @Expose
    private String representativePIDImageBefore;
    @SerializedName("RepresentativePIDImageAfter")
    @Expose
    private String representativePIDImageAfter;
    @SerializedName("IsSignContract")
    @Expose
    private String isSignContract;
    @SerializedName("QROption")
    @Expose
    private String qROption;
    @SerializedName("PaymentAccountNumber")
    @Expose
    private String paymentAccountNumber;
    @SerializedName("PaymentAccountName")
    @Expose
    private String paymentAccountName;
    @SerializedName("PaymentAccountBank")
    @Expose
    private String paymentAccountBank;
    @SerializedName("PaymentAccountBranch")
    @Expose
    private String paymentAccountBranch;
    @SerializedName("Documents")
    @Expose
    private String documents;
    @SerializedName("LinkWebsite")
    @Expose
    private String linkWebsite;
    @SerializedName("BusinessServiceID")
    @Expose
    private Integer businessServiceID;
    @SerializedName("ImagesApp")
    @Expose
    private String imagesApp;
    @SerializedName("PosID")
    @Expose
    private String posID;

    public String getPosID() {
        return posID;
    }

    public void setPosID(String posID) {
        this.posID = posID;
    }

    public Integer getiD() {
        return iD;
    }

    public void setiD(Integer iD) {
        this.iD = iD;
    }

    public String getqROption() {
        return qROption;
    }

    public void setqROption(String qROption) {
        this.qROption = qROption;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessMobile() {
        return businessMobile;
    }

    public void setBusinessMobile(String businessMobile) {
        this.businessMobile = businessMobile;
    }

    public String getFormalityType() {
        return formalityType;
    }

    public void setFormalityType(String formalityType) {
        this.formalityType = formalityType;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPrintQRName() {
        return printQRName;
    }

    public void setPrintQRName(String printQRName) {
        this.printQRName = printQRName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(Integer provinceID) {
        this.provinceID = provinceID;
    }

    public Integer getDistrictID() {
        return districtID;
    }

    public void setDistrictID(Integer districtID) {
        this.districtID = districtID;
    }

    public Integer getWardID() {
        return wardID;
    }

    public void setWardID(Integer wardID) {
        this.wardID = wardID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getRepresentativeMobile() {
        return representativeMobile;
    }

    public void setRepresentativeMobile(String representativeMobile) {
        this.representativeMobile = representativeMobile;
    }

    public String getRepresentativePosition() {
        return representativePosition;
    }

    public void setRepresentativePosition(String representativePosition) {
        this.representativePosition = representativePosition;
    }

    public String getRepresentativePIDNumber() {
        return representativePIDNumber;
    }

    public void setRepresentativePIDNumber(String representativePIDNumber) {
        this.representativePIDNumber = representativePIDNumber;
    }

    public String getRepresentativePIDImageBefore() {
        return representativePIDImageBefore;
    }

    public void setRepresentativePIDImageBefore(String representativePIDImageBefore) {
        this.representativePIDImageBefore = representativePIDImageBefore;
    }

    public String getRepresentativePIDImageAfter() {
        return representativePIDImageAfter;
    }

    public void setRepresentativePIDImageAfter(String representativePIDImageAfter) {
        this.representativePIDImageAfter = representativePIDImageAfter;
    }

    public String getIsSignContract() {
        return isSignContract;
    }

    public void setIsSignContract(String isSignContract) {
        this.isSignContract = isSignContract;
    }

    public String getQROption() {
        return qROption;
    }

    public void setQROption(String qROption) {
        this.qROption = qROption;
    }

    public String getPaymentAccountNumber() {
        return paymentAccountNumber;
    }

    public void setPaymentAccountNumber(String paymentAccountNumber) {
        this.paymentAccountNumber = paymentAccountNumber;
    }

    public String getPaymentAccountName() {
        return paymentAccountName;
    }

    public void setPaymentAccountName(String paymentAccountName) {
        this.paymentAccountName = paymentAccountName;
    }

    public String getPaymentAccountBank() {
        return paymentAccountBank;
    }

    public void setPaymentAccountBank(String paymentAccountBank) {
        this.paymentAccountBank = paymentAccountBank;
    }

    public String getPaymentAccountBranch() {
        return paymentAccountBranch;
    }

    public void setPaymentAccountBranch(String paymentAccountBranch) {
        this.paymentAccountBranch = paymentAccountBranch;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getLinkWebsite() {
        return linkWebsite;
    }

    public void setLinkWebsite(String linkWebsite) {
        this.linkWebsite = linkWebsite;
    }

    public Integer getBusinessServiceID() {
        return businessServiceID;
    }

    public void setBusinessServiceID(Integer businessServiceID) {
        this.businessServiceID = businessServiceID;
    }

    public String getImagesApp() {
        return imagesApp;
    }

    public void setImagesApp(String imagesApp) {
        this.imagesApp = imagesApp;
    }
}
