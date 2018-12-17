package stephgit.ch.memory.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import stephgit.ch.memory.persistence.dao.PlayerDAO
import stephgit.ch.memory.persistence.entity.Player

@Database(entities = arrayOf(Player::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDAO(): PlayerDAO
}