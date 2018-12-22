package ch.stephgit.memory.persistence.dao

import android.arch.persistence.room.*
import ch.stephgit.memory.persistence.entity.Game

@Dao
interface GameDAO {

    @Insert
    fun saveGame(game: Game): Long

    @Update
    fun updateGame(game: Game): Int

    @Delete
    fun deleteGame(game: Game): Int

    @Query("SELECT * FROM GAME")
    fun allGames(): List<Game>

}