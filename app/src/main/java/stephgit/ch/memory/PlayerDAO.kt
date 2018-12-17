package stephgit.ch.memory

import android.arch.persistence.room.*

@Dao
interface PlayerDAO {

    @Insert
    fun savePlayer(player: Player): Long
    
    @Update
    fun updatePlayer(player: Player): Int

    @Delete
    fun deletePlayer(player: Player): Int

    @Query("SELECT * FROM PLAYER where userName = :userName")
    fun getPlayerByUsername(userName: String): Player

    @Query("SELECT * FROM PLAYER")
    fun allPlayers(): List<Player>
}