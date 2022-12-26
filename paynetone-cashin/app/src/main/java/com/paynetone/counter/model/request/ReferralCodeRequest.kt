package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class ReferralCodeRequest(
    @SerializedName("ReferralMerchantMobile")
    val mobileNumber: String,
)