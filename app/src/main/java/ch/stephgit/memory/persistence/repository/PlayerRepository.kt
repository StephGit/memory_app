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
            "memory-db1")
            .allowMainThreadQueries()
            .build()
    }

    fun saveUser(player: Player) {
        val id = db.playerDAO().savePlayer(player)
    }

    fun findPlayerByUserName(userName: String) : Player {
        return db.playerDAO().getPlayerByUsername(userName)
    }


}