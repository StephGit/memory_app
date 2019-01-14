package ch.stephgit.memory

import android.app.Application
import android.net.Uri
import android.widget.Toast
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.persistence.repository.GameRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore


class MemoryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}
