package stephgit.ch.memory

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class OverlayMessageFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        val view = inflater.inflate(R.layout.fragment_overlay_message, container, false)
        val textView = view.findViewById<TextView>(R.id.tv_overlay_message)
        textView.text = this.arguments?.getString("score")

        return view
    }


}