package com.paynetone.counter.utils.compressor

import java.io.File

interface Constraint {
    fun isSatisfied(imageFile: File): Boolean

    fun satisfy(imageFile: File): File
}