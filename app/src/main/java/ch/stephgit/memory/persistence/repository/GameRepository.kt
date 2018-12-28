package ch.stephgit.memory.persistence.repository

import ch.stephgit.memory.GameListItem
import ch.stephgit.memory.persistence.AppDatabase
import ch.stephgit.memory.persistence.entity.Game


class GameRepository(private val db: AppDatabase) {


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