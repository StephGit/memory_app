package ch.stephgit.memory

import android.app.Application
import android.net.Uri
import android.widget.Toast
import ch.stephgit.memory.persistence.repository.GameRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore


class MemoryApp : Application() {

    private lateinit var gameRepository: GameRepository
    private lateinit var user: FirebaseUser
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()

        val db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        gameRepository = GameRepository(db)
    }

    fun getGameRepository() = gameRepository

    fun getCurrentUser() = mAuth.currentUser

    fun setCurrentUser(user: FirebaseUser) {
        this.user = user
    }

    fun saveUserInformations(username: String?, image: String?) {
        var img = image
        if (image == null) img = ""


        val profile = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .setPhotoUri(Uri.parse(img))
            .build()

        user.updateProfile(profile)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.applicationContext, "User updated", Toast.LENGTH_SHORT)
                        .show()
                }

            }
    }

}
