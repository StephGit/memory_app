package ch.stephgit.memory.persistence.dao

import android.arch.persistence.room.*
import ch.stephgit.memory.persistence.entity.Game

@Dao
interface GameDAO {

    @Insert
    fun saveGame(game: Game): Long

    @Query("SELECT * FROM GAME")
    fun allGames(): List<Game>

    @Query("SELECT * FROM GAME WHERE userId = userId")
    fun findGamesByUser(userId: Int) : List<Game>

    @Query("SELECT * FROM GAME order by score desc")
    fun findGamesByScore() : List<Game>
}