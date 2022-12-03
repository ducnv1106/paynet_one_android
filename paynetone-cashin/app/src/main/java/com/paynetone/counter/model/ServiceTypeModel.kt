package com.paynetone.counter.model

data class ServiceTypeModel(
    val id: String,
    val name: String,
    var isChecked: Boolean = true
)