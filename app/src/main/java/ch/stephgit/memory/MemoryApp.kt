package ch.stephgit.memory

import android.app.Application
import android.arch.persistence.room.Room
import ch.stephgit.memory.persistence.AppDatabase
import ch.stephgit.memory.persistence.entity.Player
import ch.stephgit.memory.persistence.repository.GameRepository
import ch.stephgit.memory.persistence.repository.PlayerRepository
import ch.stephgit.memory.persistence.service.APIClient

class MemoryApp : Application() {

    private lateinit var gameRepository: GameRepository
    private lateinit var playerRepository: PlayerRepository
    private lateinit var player: Player

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,
            "memory-db3")
            .allowMainThreadQueries()
            .build()
        gameRepository = GameRepository(db)
        playerRepository = PlayerRepository()
    }

    fun getPlayerRepository() = playerRepository

    fun getGameRepository() = gameRepository

    fun getCurrentPlayer() = player

    fun setCurrentPlayer(player: Player) {
        this.player = player
    }
}