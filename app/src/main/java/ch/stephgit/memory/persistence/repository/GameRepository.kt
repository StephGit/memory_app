package ch.stephgit.memory.persistence.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.ContentValues
import android.util.Log
import ch.stephgit.memory.GameListItem
import ch.stephgit.memory.persistence.entity.Game
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*
import kotlin.collections.ArrayList


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

    fun loadHistory(username: String): LiveData<MutableList<GameListItem>> {
        val resultList: MutableLiveData<MutableList<GameListItem>> = MutableLiveData()

        db.collection(collectionPath).whereEqualTo("username", username).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var list: MutableList<GameListItem> = ArrayList()
                    task.result?.forEach{
                        list.add(convertJsonToGame(it))
                        Log.i("LoadedHistory", it.data.toString())
                    }
                    resultList.value = list
                } else {
                    Log.e("LoadHistory", task.exception!!.message)
                }
            }
        return resultList
    }

    fun loadRanking(): LiveData<MutableList<GameListItem>> {
        val resultList: MutableLiveData<MutableList<GameListItem>> = MutableLiveData()

        db.collection(collectionPath).orderBy("flips").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var list: MutableList<GameListItem> = ArrayList()
                    task.result?.forEach{
                        list.add(convertJsonToGame(it))
                    }
                    resultList.value = list
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