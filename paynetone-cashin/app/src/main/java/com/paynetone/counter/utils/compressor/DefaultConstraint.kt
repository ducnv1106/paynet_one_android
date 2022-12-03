package com.paynetone.counter.utils.compressor

import android.graphics.Bitmap
import com.paynetone.counter.utils.decodeSampledBitmapFromFile
import com.paynetone.counter.utils.determineImageRotation
import com.paynetone.counter.utils.overWrite


import java.io.File

class DefaultConstraint(
    private val width: Int = 612,
    private val height: Int = 816,
    private val format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    private val quality: Int = 80
) : Constraint {
    private var isResolved = false

    override fun isSatisfied(imageFile: File): Boolean {
        return isResolved
    }

    override fun satisfy(imageFile: File): File {
        val result = decodeSampledBitmapFromFile(imageFile, width, height).run {
            determineImageRotation(imageFile, this).run {
                overWrite(imageFile, this, format, quality)
            }
        }
        isResolved = true
        return result
    }
}

fun Compression.default(
    width: Int = 612,
    height: Int = 816,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 80
) {
    constraint(DefaultConstraint(width, height, format, quality))
}