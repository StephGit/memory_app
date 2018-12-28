package ch.stephgit.memory

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import ch.stephgit.memory.persistence.entity.Player
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment: Fragment() {

    private lateinit var callback: OnboardingFlow
    private lateinit var player: Player

    companion object {
        fun newFragment(): Fragment = RegistrationFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            if (isValid() && isUniqueUser()) {
                (requireActivity().application as MemoryApp).setCurrentPlayer(player)
                callback.goToAgb(player)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isUniqueUser(): Boolean {
        val p = (requireActivity().application as MemoryApp).getPlayerRepository().findPlayerByUserName(et_username.text.toString().trim())

        if (p?.id == null) return true
        et_username.error = "Username is already used"
        return false
    }

    private fun isValid(): Boolean {
        val username: String = et_username.text.toString().trim()
        val password: String = et_password.text.toString().trim()
        val passwordConf: String = et_password_confirmation.text.toString().trim()
        when {
            username.isBlank() -> {
                et_username.error = "Username can't be empty!"
                return false
            }
            password.isBlank() -> {
                et_password.error = "Password can't be empty!"
                return false
            }
            passwordConf != password -> {
                et_password_confirmation.error = "Password-Confirmation must match password!"
                return false
            }
            else -> {
                player = Player(username, password)
                return true
            }
        }
    }

}