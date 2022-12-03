package com.paynetone.counter.model.response

import com.google.gson.annotations.SerializedName

data class AddressPayNetIDResponse(
    @SerializedName("WardCode")
    val wardCode: String,
    @SerializedName("ProvinceCode")
    val provinceCode: String,
    @SerializedName("DistrictCode")
    val districtCode: String,
)