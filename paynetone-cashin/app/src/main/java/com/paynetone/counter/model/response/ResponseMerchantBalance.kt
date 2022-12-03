package com.paynetone.counter.model.response

import android.os.Parcelable
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.model.MerchantBalance
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ResponseMerchantBalance(
    @SerializedName("MerchantBalance")
    val merchantBalance: @RawValue Any?
): Parcelable {
    fun getMerchantBalances():ArrayList<MerchantBalance>?{
        try {
            val gson= GsonBuilder().create()
            val json=gson.toJson(merchantBalance)
            return gson.fromJson(json,object : TypeToken<ArrayList<MerchantBalance>>(){}.type)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }
}