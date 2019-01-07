package ch.stephgit.memory.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ch.stephgit.memory.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class UserFragment : Fragment() {

    private lateinit var ivPicture: ImageView

    companion object {
        fun newFragment(): Fragment = UserFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        activity?.title= "Profile"

        var auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val view = inflater.inflate(R.layout.fragment_user, container, false)
        val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        ivPicture = view.findViewById(R.id.iv_profile_image)

        tvUsername.text = user!!.displayName
        user.photoUrl.toString().let {
            loadImageFromFirebase(it)
        }

        return view
    }

    private fun loadImageFromFirebase(imagePath: String) {
        val reference = FirebaseStorage.getInstance().getReference("profilepics/" + imagePath)
        val ONE_MEGABYTE: Long = 1024 * 1024

        reference.getBytes(ONE_MEGABYTE).addOnSuccessListener { it ->
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it!!.size)
            ivPicture.setImageBitmap(bitmap)
        }.addOnFailureListener {
            // Handle any errors
        }
    }
}
