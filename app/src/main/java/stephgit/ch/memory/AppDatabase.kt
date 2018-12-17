package stephgit.ch.memory

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import stephgit.ch.memory.Player
import stephgit.ch.memory.PlayerDAO

@Database(entities = arrayOf(Player::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDAO(): PlayerDAO
}