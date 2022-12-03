package com.paynetone.counter.model.response

import com.google.gson.annotations.SerializedName

data class PaynetGetByParentResponse(
    @SerializedName("ID")
    val id:Int,
    @SerializedName("Code")
    val code:String,
    @SerializedName("Name")
    val name:String,
    @SerializedName("ParentID")
    val ParentID:Int,
    @SerializedName("PnoLevel")
    val pnoLevel:String,
    @SerializedName("LinkID")
    val linkID:Int,
    var isChecked : Boolean = false
)