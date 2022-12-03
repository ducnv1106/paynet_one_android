package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class DashboardRequest(
    @SerializedName("MerchantID")
    val merchantID:Int,
    @SerializedName("EmpID")
    val empID:Int,
    @SerializedName("DateMode")
    val dateMode:String,
)