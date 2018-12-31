package ch.stephgit.memory.persistence.repository

import android.content.ContentValues.TAG
import android.support.constraint.solver.widgets.Snapshot
import android.util.Log
import ch.stephgit.memory.persistence.entity.Player
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PlayerRepository (private val db: FirebaseFirestore) {

    private val collectionPath: String = "player"


    fun saveUser(player: Player) {
        db.collection(collectionPath)
            .add(player)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id)
                player.id = documentReference.id
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }


    suspend fun findPlayerById(userId: String) : Player? {
        val players = db.collection(collectionPath)

        val result = players.document(userId).get().await()
        return convertJsonToPlayer(result)
    }

    suspend fun findPlayerByUserName(username: String) : Player? {
        var player: Player? = null
        val players = db.collection(collectionPath)

        val result = players.whereEqualTo("username", username).get().await()
        result.documents.forEach { document ->
            player = convertJsonToPlayer(document)
        }
        return player
    }

    private fun convertJsonToPlayer(snapshot: DocumentSnapshot?) : Player {
        val map = snapshot?.data

        return Player(
            map?.get("username") as String,
            map["password"] as String,
            map["image"] as String?,
            snapshot.reference.id
        )
    }

    suspend fun <T> Task<T>.await(): T = suspendCoroutine { continuation ->
        addOnCompleteListener { task ->
            when {
//                (task.result as QuerySnapshot).metadata.isFromCache ->
//                    continuation.resumeWithException(Exception("without internet"))
                task.isSuccessful -> continuation.context
                else -> continuation.resumeWithException(task.exception!!)
            }
        }
    }

}