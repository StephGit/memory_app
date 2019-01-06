package ch.stephgit.memory.ui.onboarding

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.Button
import android.widget.ImageView
import ch.stephgit.memory.MemoryApp
import ch.stephgit.memory.R
import java.io.File
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage


class ProfileFragment: Fragment()  {

    private lateinit var callback: OnboardingFlow

    private val profileImage = "profile_image.png"
    private lateinit var profileImageUrl: String
    private lateinit var ivImage: ImageView

    companion object {
        fun newFragment(): Fragment = ProfileFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<Button>(R.id.bt_profile_button).setOnClickListener {
            takePicture()
        }

        ivImage = view.findViewById(R.id.iv_profile_image)
        return view
    }

    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, 1)
    }


    private fun checkForPermissions(requiredPermission: String, permissionRequest: Int) {
        if (ContextCompat.checkSelfPermission(requireContext(),
                requiredPermission)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    requiredPermission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(requiredPermission),
                    permissionRequest)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            (requireActivity().application as MemoryApp).saveUserInformations(null, profileImageUrl)
            callback.finishOnboarding()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            imageBitmap?.let {
                ivImage.setImageBitmap(it)
                val file = File(requireContext().filesDir, profileImage)
                file.outputStream().use { f ->
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, f)
                }
                uploadImageToFirebaseStorage(Uri.parse(file.absolutePath))
            }
        }
    }

    private fun uploadImageToFirebaseStorage(uri: Uri) {
        val profilImageRef =
            FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg")

        profilImageRef.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                profileImageUrl = taskSnapshot.uploadSessionUri.toString()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }

    }
}