package ch.stephgit.memory.persistence.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Game (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val userId: Int,
    val date: Date,
    val score: Int
)