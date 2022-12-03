package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class RequestPayNetHasChildren(
    @SerializedName("PaynetID")
    val payNetID: Int
)