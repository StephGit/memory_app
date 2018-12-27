package ch.stephgit.memory.persistence.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity
data class Game (
    val userName: String,
    val date: Date,
    val flips: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)