package ch.stephgit.memory.persistence.entity

data class Player (
    val username: String,
    val password: String,
    var image: String? = null,
    var id: String? = null
)

