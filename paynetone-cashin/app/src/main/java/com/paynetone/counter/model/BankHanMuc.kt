package com.paynetone.counter.model

import androidx.annotation.DrawableRes
import com.paynetone.counter.R

data class BankHanMuc(
    @DrawableRes
    var image: Int = R.drawable.logo_18_viettinbank,
    val fullName: String,
    val nameBank: String,
    val accountNumber: String
)