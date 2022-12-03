package com.paynetone.counter.model.response

import com.google.gson.annotations.SerializedName

data class AddPINCodeResponse(
    @SerializedName("Pin")
    val pinCode:String?
)