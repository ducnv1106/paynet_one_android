package com.paynetone.counter.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChangePassByOTPRequest(
    @SerializedName("MobileNumber")
    @Expose
    var mobileNumber: String? = null,
    @SerializedName("OTPType")
    @Expose
    var oTPType: String = "N",
    @SerializedName("OTPValue")
    @Expose
    var opTPValue: String? = null,
    @SerializedName("PasswordNew")
    @Expose
    var passwordNew: String? = null,
)