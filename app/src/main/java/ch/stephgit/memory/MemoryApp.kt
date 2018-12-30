package ch.stephgit.memory

import android.app.Application
import ch.stephgit.memory.persistence.entity.Player
import ch.stephgit.memory.persistence.repository.GameRepository
import ch.stephgit.memory.persistence.repository.PlayerRepository
import com.google.firebase.firestore.FirebaseFirestore

class MemoryApp : Application() {

    private lateinit var gameRepository: GameRepository
    private lateinit var playerRepository: PlayerRepository
    private lateinit var player: Player

    override fun onCreate() {
        super.onCreate()

        val db = FirebaseFirestore.getInstance()
        gameRepository = GameRepository(db)
        playerRepository = PlayerRepository(db)


    }

    fun getPlayerRepository() = playerRepository

    fun getGameRepository() = gameRepository

    fun getCurrentPlayer() = player

    fun setCurrentPlayer(player: Player) {
        this.player = player
    }
}