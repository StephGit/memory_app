package ch.stephgit.memory.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ch.stephgit.memory.persistence.dao.GameDAO
import ch.stephgit.memory.persistence.dao.PlayerDAO
import ch.stephgit.memory.persistence.entity.Player

@Database(entities = arrayOf(Player::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDAO(): PlayerDAO
    abstract fun gameDAO(): GameDAO
}