package ch.stephgit.memory.persistence.repository

import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.Toast
import ch.stephgit.memory.GameListItem
import ch.stephgit.memory.MainActivity
import ch.stephgit.memory.MemoryApp
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
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    fun loadHistory(username: String): MutableList<GameListItem> {
        val games = db.collection(collectionPath)
        val resultList: MutableList<GameListItem> = ArrayList()
        val gameQuery = games.whereEqualTo("username", username)
        gameQuery.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.forEach{
                        resultList.add(convertJsonToGame(it))}
                } else {
                    Log.e("LoadHistory", task.exception!!.message)
                }
            }
        return resultList
    }

    fun loadRanking(): MutableList<GameListItem> {
        val games = db.collection(collectionPath)
        val resultList: MutableList<GameListItem> = ArrayList()
        val gameQuery = games.orderBy("flips")
        gameQuery.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.forEach{
                        resultList.add(convertJsonToGame(it))}
                } else {
                    Log.e("LoadRanking", task.exception!!.message)
                }
            }
        return resultList
    }

    private fun convertJsonToGame(it: QueryDocumentSnapshot?): GameListItem {
        val map = it?.data
        return GameListItem(
            map?.get("username") as String,
            map?.get("date") as Date,
            map?.get("flips") as Long)
    }

}