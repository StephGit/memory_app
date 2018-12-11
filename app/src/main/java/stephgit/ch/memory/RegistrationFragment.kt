package stephgit.ch.memory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.*
import kotlinx.android.synthetic.main.fragment_registration.*
import java.lang.RuntimeException

class RegistrationFragment: Fragment() {

    private lateinit var callback: OnboardingFlow

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            if (validate()) {
                callback.goToAgb()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun validate(): Boolean {
        val username = et_username.text.toString().trim()
        if (username.isBlank()) {
            et_username.error = "Username darf nicht leer sein"
            return false
        }

        return true
    }

}