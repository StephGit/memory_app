package ch.stephgit.memory.persistence.repository

import android.arch.persistence.room.Room
import android.content.Context
import ch.stephgit.memory.GameListItem
import ch.stephgit.memory.persistence.AppDatabase
import ch.stephgit.memory.persistence.entity.Game


class GameRepository(private val applicationContext: Context) {


    private var db: AppDatabase

    init {
        db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,
            "memory-db2")
            .allowMainThreadQueries()
            .build()
    }

    fun saveGame(game: Game) {
        val id = db.gameDAO().saveGame(game)
    }

    fun loadHistory(userName: String): MutableList<GameListItem> {
        val list = db.gameDAO().findGamesByUser(userName)
        val resultList: MutableList<GameListItem> = ArrayList()
        list.forEach{ it ->
            resultList.add(GameListItem(it.userName, it.date, it.flips))
        }
        return resultList
    }

    fun loadRanking(): List<Game> {
        return db.gameDAO().findGamesByFlips()
    }



}