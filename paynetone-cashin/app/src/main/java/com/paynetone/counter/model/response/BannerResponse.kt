package com.paynetone.counter.model.response

import com.google.gson.annotations.SerializedName

data class BannerResponse(
    @SerializedName("ID")
    val id:Int,
    @SerializedName("BannerValue")
    val bannerValue:String,
    @SerializedName("BannerName")
    val bannerName:String,
)