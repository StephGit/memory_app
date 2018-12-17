package stephgit.ch.memory.persistence.dao

import android.arch.persistence.room.*
import stephgit.ch.memory.persistence.entity.Game

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