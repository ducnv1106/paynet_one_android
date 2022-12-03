package com.paynetone.counter.model.response

import com.google.gson.annotations.SerializedName

data class TopUpAddressResponse(
    @SerializedName("ReturnUrl")
    val returnUrl: String
)