package com.paynetone.counter.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdatePasswordRequest(
    @SerializedName("MobileNumber")
    @Expose
    var mobileNumber: String? = null,
    @SerializedName("Password")
    @Expose
    var password: String? = null,
    @SerializedName("PasswordNew")
    @Expose
    var passwordNew: String? = null,
)