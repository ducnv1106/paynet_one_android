package com.paynetone.counter.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetProviderResponse(
    @SerializedName("ID")
    val id:Int?=null,

    @SerializedName("Code")
    val code: String?=null,

    @SerializedName("Name")
    val name: String?=null,

    @SerializedName("Type")
    val type: Int?=null,

    @SerializedName("Category")
    val category: Int?=null,

    @SerializedName("Icon")
    val icon: String?=null,

    @SerializedName("PaymentType")
    val paymentType:Int?=null,

    @SerializedName("IsActive")
    val isActive:String?=null,

    @SerializedName("ProviderACNTCode")
    val providerACNTCode:String?=null,

    var itemGroup:Boolean = false,

    var imageGroup : ArrayList<String> = arrayListOf()
): Parcelable