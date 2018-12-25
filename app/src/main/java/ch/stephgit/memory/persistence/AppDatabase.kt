package ch.stephgit.memory.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ch.stephgit.memory.persistence.dao.GameDAO
import ch.stephgit.memory.persistence.dao.PlayerDAO
import ch.stephgit.memory.persistence.entity.Game
import ch.stephgit.memory.persistence.entity.Player

@Database(entities = [Player::class, Game::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDAO(): PlayerDAO
    abstract fun gameDAO(): GameDAO
}