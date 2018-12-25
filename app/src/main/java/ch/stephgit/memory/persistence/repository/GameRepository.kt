package ch.stephgit.memory.persistence.repository

import android.arch.persistence.room.Room
import android.content.Context
import ch.stephgit.memory.persistence.AppDatabase
import ch.stephgit.memory.persistence.entity.Game


class GameRepository(private val applicationContext: Context) {


    private var db: AppDatabase

    init {
        db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,
            "memory-db1")
            .allowMainThreadQueries()
            .build()
    }

    fun saveGame(game: Game) {
        // TODO
        db.gameDAO().saveGame(game)

    }

    fun loadHistory(userId: Int) {
        db.gameDAO().findGamesByUser(userId)
    }

    fun loadRanking(): List<Game> {
        return db.gameDAO().findGamesByScore()
    }



}