package ch.stephgit.memory.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.*
import android.widget.TextView
import ch.stephgit.memory.R
import kotlinx.android.synthetic.main.fragment_agb.*


class AGBFragment: Fragment() {

    private lateinit var callback: OnboardingFlow

    companion object {
        fun newFragment(): Fragment = AGBFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_agb, container, false)
        val agbText = resources.openRawResource(R.raw.agb).bufferedReader().use {
            it.readText()
        }

        val tvAgb = view.findViewById<TextView>(R.id.tv_agb)
        tvAgb.text = Html.fromHtml(agbText).toString()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            if (validate()) {
                callback.goToProfile()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validate(): Boolean {
        if (cb_agb.isChecked) return true
        cb_agb.error = "Please accept AGB's to proceed"
        return false
    }
}