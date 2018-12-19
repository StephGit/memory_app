package stephgit.ch.memory

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_profile.*
import android.view.*
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.os.Environment.getExternalStorageDirectory
import java.io.File


class ProfileFragment: Fragment()  {

    private lateinit var callback: OnboardingFlow

    private val REQUEST_IMAGE_CAPTURE = 1

    private val PROFILE_IMAGE_FILE_NAME = "profile_image.png"
    private val filesDir = Environment.getExternalStorageDirectory()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        bt_profile_button.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

        val imageBitmap = BitmapFactory.decodeFile(File(filesDir, PROFILE_IMAGE_FILE_NAME).absolutePath)
        imageBitmap?.let {
            iv_profile_image.setImageBitmap(it)
        }


        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            callback.finishOnboarding()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            val file = File(filesDir, PROFILE_IMAGE_FILE_NAME)
            if (imageBitmap != null) {
                file.outputStream().use {
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                }
                iv_profile_image.setImageBitmap(imageBitmap)
//            } else {
//                Log.w(LOG_TAG, "ImageBitmap from intent was null")
            }
        }
    }

//    fun takePicture() {
//        val intent = Intent("android.media.action.IMAGE_CAPTURE")
//        val photo = File(Environment.getExternalStorageDirectory(), "Pic.jpg")
//        intent.putExtra(
//            MediaStore.EXTRA_OUTPUT,
//            Uri.fromFile(photo)
//        )
//        imageUri = Uri.fromFile(photo)
//        this@PhotosListFragment.startActivityForResult(intent, 100)
//    }
}