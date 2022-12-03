package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class RequestOtp(
    @SerializedName("MobileNumber")
    val mobileNumber: String,
    @SerializedName("OTPType")
    val oTPType: String = "N",
    @SerializedName("OTPValue")
    val oTPValue: String,
)