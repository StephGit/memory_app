package ch.stephgit.memory.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ch.stephgit.memory.R

class OverlayMessageFragment: Fragment() {

    companion object {
        fun newFragment(): Fragment = OverlayMessageFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_overlay_message, container, false)
        val textView = view.findViewById<TextView>(R.id.tv_overlay_message)
        val message = "Ok! You had " + this.arguments?.getLong("flips")
        textView.text =  message

        view.findViewById<Button>(R.id.btn_goto_newgame).setOnClickListener {
            startActivity(MainActivity.newIntent(requireContext())
        )}
        return view
    }


}