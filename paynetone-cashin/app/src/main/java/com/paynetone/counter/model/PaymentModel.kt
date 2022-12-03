package com.paynetone.counter.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentModel(
    val id:Int,
    val name:String,
    @DrawableRes
    val imageLogo: Int,
    var isChecked : Boolean=false
): Parcelable