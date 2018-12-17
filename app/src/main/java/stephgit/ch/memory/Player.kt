package stephgit.ch.memory

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Player (
    val userName: String,
    val password: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)

