package stephgit.ch.memory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.*
import kotlinx.android.synthetic.main.fragment_agb.*


class AGBFragment: Fragment() {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, AGBFragment::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_agb, container, false)
        val agbText = resources.openRawResource(R.raw.agb).bufferedReader().use {
            it.readText()
        }
        tv_agb.text = Html.fromHtml(agbText).toString()
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            if (validate()) {
                startActivity(ProfileActivity.newIntent(requireContext()))
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