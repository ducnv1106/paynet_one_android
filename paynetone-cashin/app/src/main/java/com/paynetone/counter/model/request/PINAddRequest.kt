package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class PINAddRequest(
    @SerializedName("EmpID")
    val empID:Int?,
    @SerializedName("PIN")
    val pIN:String?,
    @SerializedName("Password")
    val password:String?=null,
    @SerializedName("MobileNumber")
    val mobileNumber:String?
)