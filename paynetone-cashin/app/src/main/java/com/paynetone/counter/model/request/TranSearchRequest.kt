package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class TranSearchRequest (
    @SerializedName("PaynetID")
    var paynetID:Int,
    @SerializedName("ReturnCode")
    val returnCode :Int?=null,
    @SerializedName("FromDate")
    val fromDate :Int?=null,
    @SerializedName("ToDate")
    val toDate:Int?=null,
    @SerializedName("RetRefNumber")
    val RetRefNumber :String?=null,
)