package com.example.mobile.data

import android.graphics.Bitmap

data class Post(
    var titre: String,
    var note: String,
    var budget: String,
    var publication: String,
    var description: String,
    var image: Int
) {
    val bitmap: Bitmap? = null
    var jaime: Int = 0
    var id: Any? = null
}
