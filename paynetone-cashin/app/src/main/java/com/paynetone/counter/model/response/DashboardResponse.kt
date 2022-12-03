package com.paynetone.counter.model.response

import android.os.Parcelable
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.model.MerchantBalance
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class DashboardResponse(
    @SerializedName("listThreeDay")
    val listThreeDay: @RawValue Any?,
    @SerializedName("listDateCustomize")
    val listDateCustomize: @RawValue Any?
) : Parcelable{
    fun getDataDashboard():ArrayList<DashboardData>?{
        try {
            val gson= GsonBuilder().create()
            val json=gson.toJson(listDateCustomize)
            return gson.fromJson(json,object : TypeToken<ArrayList<DashboardData>>(){}.type)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }
    fun getStatisticals():ArrayList<Statistical>?{
        try {
            val gson= GsonBuilder().create()
            val json=gson.toJson(listThreeDay)
            return gson.fromJson(json,object : TypeToken<ArrayList<Statistical>>(){}.type)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }
}
data class Statistical(
    @SerializedName("Yield")
    val yield:Int,
    @SerializedName("TransAmount")
    val transAmount:Long,
    @SerializedName("TransDate")
    val transDate:String,
    @SerializedName("CountQROnline")
    val countQROnline:Int,
    @SerializedName("CountGTGT")
    val countGTGT:Int,
    @SerializedName("CountINSTA")
    val countINSTA:Int,
    @SerializedName("SumQROnline")
    val sumQROnline:Int,
    @SerializedName("SumGTGT")
    val sumGTGT:Int,
    @SerializedName("SumINSTA")
    val sumINSTA:Int,
    @SerializedName("NameDate")
    val nameDate:String,

)
data class DashboardData(
    @SerializedName("Yield")
    val yield:Int,
    @SerializedName("TransAmount")
    val transAmount:Long,
    @SerializedName("CountQROnline")
    val countQROnline:Int,
    @SerializedName("CountGTGT")
    val countGTGT:Int,
    @SerializedName("CountINSTA")
    val countINSTA:Int,
    @SerializedName("SumQROnline")
    val sumQROnline:Long,
    @SerializedName("SumGTGT")
    val sumGTGT:Long,
    @SerializedName("SumINSTA")
    val sumINSTA:Long,
)