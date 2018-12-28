package ch.stephgit.memory.persistence.repository

import ch.stephgit.memory.persistence.AppDatabase
import ch.stephgit.memory.persistence.entity.Player

class PlayerRepository (private val db: AppDatabase) {


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