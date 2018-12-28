package ch.stephgit.memory.onboarding

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.EditText
import android.widget.TextView
import ch.stephgit.memory.MemoryApp
import ch.stephgit.memory.R
import ch.stephgit.memory.persistence.entity.Player
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment: Fragment() {

    private lateinit var callback: OnboardingFlow

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var passwordConf: EditText


    companion object {
        fun newFragment(): Fragment = RegistrationFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        username = view.findViewById(R.id.et_username)
        password = view.findViewById(R.id.et_password)
        passwordConf = view.findViewById(R.id.et_password_confirmation)

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            if (isValid() && isUniqueUser()) {
                (requireActivity().application as MemoryApp)
                    .setCurrentPlayer(
                        Player(username.text.toString().trim(), password.text.toString().trim()))
                callback.goToAgb()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isUniqueUser(): Boolean {
        val p = (requireActivity().application as MemoryApp).getPlayerRepository().findPlayerByUserName(username.text.toString().trim())

        if (p?.id == null) return true
        username.error = "Username is already used"
        return false
    }

    private fun isValid(): Boolean {
        return when {
            username.text.trim().isBlank() -> {
                et_username.error = "Username can't be empty!"
                false
            }
            password.text.trim().isBlank() -> {
                et_password.error = "Password can't be empty!"
                false
            }
            passwordConf.text.toString() != password.text.toString() -> {
                et_password_confirmation.error = "Password-Confirmation must match password!"
                false
            }
            else -> true
        }
    }

}