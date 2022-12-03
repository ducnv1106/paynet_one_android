package com.paynetone.counter.model

import android.graphics.Color
import java.util.*

data class PhoneContact(
    val name:String? = null,
    val phone:String? = null,
    var isSelected:Boolean = false,
    val color:Int = Color.argb(
        255,
        Random().nextInt(256),
        Random().nextInt(256),
        Random().nextInt(256)
    )
)