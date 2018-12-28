package ch.stephgit.memory.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Bitmap

@Entity
data class Player (
    val userName: String,
    val password: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: Bitmap? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)

