package ch.stephgit.memory.persistence.repository

import android.arch.persistence.room.Room
import android.content.Context
import ch.stephgit.memory.persistence.AppDatabase
import ch.stephgit.memory.persistence.entity.Player

class PlayerRepository (private val applicationContext: Context) {


    private var db: AppDatabase

    init {
        db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,
            "memory-db2")
            .allowMainThreadQueries()
            .build()
    }

    fun saveUser(player: Player) : Long {
        return db.playerDAO().savePlayer(player)
    }

    fun findPlayerById(userId: Long) : Player {
        return db.playerDAO().getPlayerById(userId)
    }

    fun findPlayerByUserName(userName: String) : Player {
        return db.playerDAO().getPlayerByUsername(userName)
    }


}