package com.paynetone.counter.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.File

@Parcelize
data class Media(
    val uri: Uri,
    val isVideo: Boolean,
    val path:String ="",
    val file:File?=null
): Parcelable