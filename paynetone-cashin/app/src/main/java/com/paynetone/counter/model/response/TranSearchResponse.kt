package com.paynetone.counter.model.response

import com.google.gson.annotations.SerializedName

data class TranSearchResponse(
    @SerializedName("TransDate")
    val transDate :String,
    @SerializedName("RetRefNumber")
    val retRefNumber :String,
    @SerializedName("Amount")
    val amount :Int?,
    @SerializedName("Fee")
    val fee :Int?,
    @SerializedName("TransCategory")
    val transCategory :Int?,
    @SerializedName("TransAmount")
    val transAmount :Int?,
    @SerializedName("TransReason")
    val transReason :String?,
    @SerializedName("ReturnCode")
    val returnCode :Int?,
    @SerializedName("MobileNumber")
    val mobileNumber:String?,
    @SerializedName("ProviderAcntName")
    val providerAcntName:String

)