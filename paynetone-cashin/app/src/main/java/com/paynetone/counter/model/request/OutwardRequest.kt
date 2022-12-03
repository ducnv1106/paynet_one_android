package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class OutwardRequest(
    @SerializedName("MerchantID")
    val merchantID:Int,
    @SerializedName("BalanceType")
    val balanceType:String
)