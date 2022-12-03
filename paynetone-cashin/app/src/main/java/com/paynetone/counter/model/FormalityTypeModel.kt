package com.paynetone.counter.model

data class FormalityTypeModel(
    val id: String,
    val name: String,
    var isChecked: Boolean = true
)