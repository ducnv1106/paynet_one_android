package com.paynetone.counter.model.response

import com.google.gson.annotations.SerializedName

data class PostIdResponse(
    @SerializedName("ID")
    val id:Int,
    @SerializedName("ProvinceID")
    val provinceID:Int,
    @SerializedName("PosCode")
    val posCode:Int,
)