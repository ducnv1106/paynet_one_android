package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class RequestGetOtp(
    @SerializedName("MobileNumber")
    val mobileNumber: String,
    @SerializedName("OTPType")
    val oTPType: String = "N"
)