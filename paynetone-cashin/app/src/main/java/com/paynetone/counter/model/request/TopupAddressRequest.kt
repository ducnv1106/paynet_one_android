package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class TopupAddressRequest(
    @SerializedName("Code")
    val code: String
)