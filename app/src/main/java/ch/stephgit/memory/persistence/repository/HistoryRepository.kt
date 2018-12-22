package ch.stephgit.memory.persistence.repository

import android.arch.persistence.room.Room
import android.content.Context
import ch.stephgit.memory.persistence.AppDatabase
import ch.stephgit.memory.persistence.entity.Player

class HistoryRepository(private val applicationContext: Context) {


    private var db: AppDatabase

    init {
        db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,
            "memory-db1")
            .allowMainThreadQueries()
            .build()
    }

    fun saveUser(player: Player) {
        db.playerDAO().savePlayer(player)

    }

    fun loadUsers() {
        var players: List<Player> = db.playerDAO().allPlayers()
        for ( p in players) {
            print(p.userName)
        }
    }




}