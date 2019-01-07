package ch.stephgit.memory.persistence.entity

import android.graphics.Bitmap

data class Player (
    val userName: String,
    var password: String? = null,
    var image: String? = null,
    val id: Int? = null
)

