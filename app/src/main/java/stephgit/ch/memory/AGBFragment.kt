package stephgit.ch.memory

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.*
import android.widget.TextView


class AGBFragment: Fragment() {

    private lateinit var callback: OnboardingFlow

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

        var tv_ag = view.findViewById<TextView>(R.id.tv_agb)
        tv_ag.text = Html.fromHtml(agbText).toString()
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

    fun validate(): Boolean {
        // TODO add agb validator
        return true
    }
}