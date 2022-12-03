package com.paynetone.counter.model.request

import com.google.gson.annotations.SerializedName

data class PartNerAddressRequest(
    @SerializedName("Channel")
    val channel:String = "ANDROID",
    @SerializedName("ProviderACNTCode")
    val providerACNTCode:String,
    @SerializedName("CounterCode")
    val counterCode:String,
    @SerializedName("ProvinceCode")
    val provinceCode:String,
    @SerializedName("DistrictCode")
    val districtCode:String,
    @SerializedName("WardCode")
    val wardCode:String
)