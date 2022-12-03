package com.paynetone.counter.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WalletResponse(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("Code")
    val code: String,
    @SerializedName("Name")
    val name: String
): Parcelable {
    var isChecked:Boolean = false
}