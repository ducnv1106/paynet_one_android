package com.paynetone.counter.model

data class ImagePicker(
    var uri: String="",
    var isChecked: Boolean = false,
    var isCamera: Boolean=false,
    var path:String =""

)