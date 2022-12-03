package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class WithdrawSearchRequest(
    @SerializedName("MerchantID")
    val MerchantID:Int,
    @SerializedName("BalanceType")
    val balanceType:String
)