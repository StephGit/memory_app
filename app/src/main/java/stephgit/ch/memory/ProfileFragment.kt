package stephgit.ch.memory

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.*

class ProfileFragment: Fragment()  {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            PreferenceManager.getDefaultSharedPreferences(requireContext())
                .edit()
                .putString("KEY_TOKEN", "someToken")
                .apply()
            startActivity(MainActivity.newIntent(requireContext()))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}