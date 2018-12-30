package ch.stephgit.memory

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Base64
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

        tvUsername.text = player.username
        player.image?.let {
            val byteArray = Base64.decode(it, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
            ivPicture.setImageBitmap(bitmap)
        }
        return view
    }
}
