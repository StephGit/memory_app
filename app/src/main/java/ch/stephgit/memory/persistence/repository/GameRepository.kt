package ch.stephgit.memory.persistence.repository

import android.arch.lifecycle.MutableLiveData
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import ch.stephgit.memory.persistence.entity.Game
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.lang.Exception
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

    fun loadHistory(username: String): MutableLiveData<List<Game>> {
        val resultList: MutableLiveData<List<Game>> = MutableLiveData()

        db.collection(collectionPath).whereEqualTo("username", username).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list: MutableList<Game> = ArrayList()
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

    fun loadRanking(): MutableLiveData<List<Game>> {
        val resultList: MutableLiveData<List<Game>> = MutableLiveData()

        val docRef = db.collection(collectionPath).orderBy("flips")
        docRef.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@EventListener
            }
            if ( value != null) {
                val list: MutableList<Game> = ArrayList()
                for (doc in value) {
                    list.add(convertJsonToGame(doc))
                }
                resultList.value = list
            }
        })
        return resultList
    }

    private fun convertJsonToGame(it: QueryDocumentSnapshot?): Game {
        val map = it?.data
        return Game(
            map?.get("username") as String,
            map?.get("date") as Date,
            map?.get("flips") as Long,
            it.id
        )
    }

}