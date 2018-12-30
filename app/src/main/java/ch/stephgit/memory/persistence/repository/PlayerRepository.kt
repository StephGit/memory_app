package ch.stephgit.memory.persistence.repository

import android.content.ContentValues.TAG
import android.util.Log
import ch.stephgit.memory.persistence.entity.Player
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

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


    fun findPlayerById(userId: String) : Player {
        val players = db.collection(collectionPath)

        val player =  players.document(userId)
        return convertJsonToPlayer(player.get().result)

    }

    fun findPlayerByUserName(username: String) : Player? {

        val players = db.collection(collectionPath)

        val playerQuery = players.whereEqualTo("username", username)
        playerQuery.get().result?.forEach {
            return convertJsonToPlayer(it)
        }
        return null
    }

    private fun convertJsonToPlayer(snapshot: DocumentSnapshot?) : Player {
        val map = snapshot?.data
        return Player(
            map?.get("username") as String,
            map?.get("password") as String,
            map?.get("image") as String?,
            snapshot?.reference?.id
        )
    }

}