package stephgit.ch.memory

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*

class ProfileFragment: Fragment()  {

    private lateinit var callback: OnboardingFlow

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }



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
            callback.finishOnboarding()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}