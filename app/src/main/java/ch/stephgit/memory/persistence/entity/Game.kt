package ch.stephgit.memory.persistence.entity

import java.util.*

data class Game (
    val username: String,
    val date: Date,
    val flips: Int,
    var id: String? = null
)