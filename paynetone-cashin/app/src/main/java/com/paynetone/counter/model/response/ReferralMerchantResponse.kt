package com.paynetone.counter.model.response

import com.google.gson.annotations.SerializedName

data class ReferralMerchantResponse(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("ReferralMerchantMobile")
    val referralMerchantMobile: String,
    @SerializedName("ReferralMerchantCode")
    val referralMerchantCode: String,
    @SerializedName("NewMerchantMobile")
    val newMerchantMobile: String,
    @SerializedName("NewMerchantCode")
    val newMerchantCode: String,
    @SerializedName("CreateDate")
    val createDate: String,
    @SerializedName("StatusDate")
    val statusDate: String,
    @SerializedName("Status")
    val status: String,
    @SerializedName("ReferralMerchantAmount")
    val referralMerchantAmount: Int,
    @SerializedName("NewMerchantAmount")
    val newMerchantAmount: Int,
)