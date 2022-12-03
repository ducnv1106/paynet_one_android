package com.paynetone.counter.model.response

import com.google.gson.annotations.SerializedName

data class PayNetHasChildrenResponse(
    @SerializedName("hasChildren")
    val hasChildren: String
)