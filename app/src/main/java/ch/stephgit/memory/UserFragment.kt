package ch.stephgit.memory

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class UserFragment : Fragment() {

    companion object {
        fun newFragment(): Fragment = UserFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        activity?.title= "Profile"

        val view = inflater.inflate(R.layout.fragment_user, container, false)
        val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        val ivPicture = view.findViewById<ImageView>(R.id.iv_profile_image)

        val player = (requireActivity().application as MemoryApp).getCurrentPlayer()

        tvUsername.text = player.userName
        player.image?.let {
//            ivPicture.setImageBitmap(it)
        }
        return view
    }

}
