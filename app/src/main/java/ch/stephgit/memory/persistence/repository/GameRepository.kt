package ch.stephgit.memory.persistence.repository

import android.content.ContentValues
import android.util.Log
import ch.stephgit.memory.GameListItem
import ch.stephgit.memory.persistence.entity.Game
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*


class GameRepository(private val db: FirebaseFirestore) {

    private val collectionPath: String = "game"

    fun saveGame(game: Game) {
        db.collection(collectionPath)
            .add(game)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: " + documentReference.id)
                game.id = documentReference.id
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    fun loadHistory(username: String): MutableList<GameListItem> {
        val games = db.collection(collectionPath)
        val resultList: MutableList<GameListItem> = ArrayList()
        val gameQuery = games.whereEqualTo("username", username)
        gameQuery.get().result?.forEach {
            resultList.add(convertJsonToGame(it))
        }
        return resultList
    }

    fun loadRanking(): MutableList<GameListItem> {
        val games = db.collection(collectionPath)
        val resultList: MutableList<GameListItem> = ArrayList()
        val gameQuery = games.orderBy("flips")
        gameQuery.get().result?.forEach {
            resultList.add(convertJsonToGame(it))
        }
        return resultList
    }

    private fun convertJsonToGame(it: QueryDocumentSnapshot?): GameListItem {
        val map = it?.data
        return GameListItem(
            map?.get("username") as String,
            map?.get("date") as Date,
            map?.get("flips") as Int)
    }

}