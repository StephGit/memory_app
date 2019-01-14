package ch.stephgit.memory.persistence.repository

import android.arch.lifecycle.MutableLiveData
import android.net.Uri
import android.nfc.Tag
import android.util.Log
import android.view.View
import android.widget.Toast
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class UserRepository @Inject constructor(private val auth: FirebaseAuth) {

    init {
        Injector.appComponent.inject(this)
    }

    fun getCurrentUser() : FirebaseUser? {
        return auth.currentUser
    }

    fun logout() {
        auth.signOut()
    }

    fun registerUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserInformations(username, null)
                        // TODO return liveData
                    } else {
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            // TODO return liveData
                        } else {
                            // TODO return liveData for task.exception!!.message
                        }
                    }
                }
    }

    fun saveUserInformations(username: String?, image: String?) {
        var img = image
        if (image == null) img = ""


        val profile = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .setPhotoUri(Uri.parse(img))
            .build()

        auth.currentUser!!.updateProfile(profile)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    Toast.makeText(this.applicationContext, "User updated", Toast.LENGTH_SHORT)
//                        .show() TODO move to fragment
                }

            }
    }

    fun login(email: String, password: String) : MutableLiveData<Boolean> {
        var bool: MutableLiveData<Boolean> = MutableLiveData()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //TODO return liveData
                    bool.value = true
                } else {
                    //TODO return liveData for task.exception!!.message
                    Log.d("login", task.exception!!.message)
                    bool.value = false
                }
            }
        return bool
    }
}