package com.paynetone.counter.model

import com.google.gson.annotations.SerializedName

data class PostIdRequest(
    @SerializedName("ID")
    val id: Int
)