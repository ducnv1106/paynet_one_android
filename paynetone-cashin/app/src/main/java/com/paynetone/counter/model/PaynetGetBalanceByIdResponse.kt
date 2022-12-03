package com.paynetone.counter.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaynetGetBalanceByIdResponse(

    @SerializedName("ID")
    val id: Int,
    @SerializedName("Code")
    val code: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Amount")
    val amount: Int,
    @SerializedName("LinkID")
    val linkID: Int,
    @SerializedName("PaynetID")
    val payNetID:Int
): Parcelable