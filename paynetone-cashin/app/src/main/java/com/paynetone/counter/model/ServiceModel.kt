package com.paynetone.counter.model

import androidx.annotation.DrawableRes

data class ServiceModel(
    val id:Int,
    @DrawableRes
    val image:Int,
    val name:String
)