package stephgit.ch.memory

import android.arch.persistence.room.*

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