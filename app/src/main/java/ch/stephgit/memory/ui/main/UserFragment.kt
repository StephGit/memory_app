package ch.stephgit.memory.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ch.stephgit.memory.R
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.persistence.repository.ImageRepository
import com.google.common.base.Optional
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class UserFragment : Fragment() {

    @Inject
    lateinit var currentUser: Optional<FirebaseUser>

    @Inject
    lateinit var imageRepository: ImageRepository

    private lateinit var ivPicture: ImageView

    companion object {
        fun newFragment(): Fragment = UserFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Injector.appComponent.inject(this)
        setHasOptionsMenu(true)
        activity?.title= "Profile"

        val view = inflater.inflate(R.layout.fragment_user, container, false)
        val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        ivPicture = view.findViewById(R.id.iv_profile_image)

        if (currentUser.isPresent) {


            tvUsername.text = currentUser.get().displayName
            currentUser.get().photoUrl.let {
                imageRepository.loadImage(it.toString())
                    .observe(this, Observer { pic ->
                        ivPicture.setImageBitmap(pic)
                    })
            }
        }

        return view
    }
}
