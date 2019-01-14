package ch.stephgit.memory.persistence.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.Toast
import ch.stephgit.memory.R.id.progressBar
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class ImageRepository  @Inject constructor(private val storage: FirebaseStorage)  {

    fun loadImage(imagePath: String) : MutableLiveData<Bitmap> {

        val reference = storage.getReference("profilepics/" + imagePath)
        val ONE_MEGABYTE: Long = 1024 * 1024
        var data: MutableLiveData<Bitmap> = MutableLiveData()

        reference.getBytes(ONE_MEGABYTE).addOnSuccessListener { it ->
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it!!.size)
            data.value = bitmap
        }.addOnFailureListener {
//            data.value = it.message
        }
        return data
    }

    fun storeImage(uri: Uri) : MutableLiveData<String> {

        val profilImageRef =
            storage.getReference("profilepics/" + System.currentTimeMillis() + ".png")
        var data: MutableLiveData<String> = MutableLiveData()

        profilImageRef.putFile(uri)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    data.value = profilImageRef.name
                } else {
                    data.value = task.exception!!.message
                }
            }
        return data
    }
}