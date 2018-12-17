package stephgit.ch.memory

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import kotlinx.android.synthetic.main.fragment_registration.*
import stephgit.ch.memory.persistence.entity.Player
import stephgit.ch.memory.persistence.repository.PlayerRepository
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
            if (isValid() && isUniqueUser()) {
                callback.goToAgb(
                    Player(
                        et_username.text.toString().trim(),
                        et_password.text.toString().trim()
                    )
                )
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isUniqueUser(): Boolean {
        val playerRepository: PlayerRepository = PlayerRepository(super.getContext()!!.applicationContext)
        var p = playerRepository.findPlayerByUserName(et_username.text.toString().trim())

        if (p?.id == null) return true

        et_username.error = "Username ist bereits vergeben!"
        return false
    }

    fun isValid(): Boolean {
        val username: String = et_username.text.toString().trim()
        val password: String = et_password.text.toString().trim()
        val passwordConf: String = et_password_confirmation.text.toString().trim()
        if (username.isBlank()) {
            et_username.error = "Username darf nicht leer sein"
            return false
        } else if (password.isBlank()) {
            et_password.error = "Passwort darf nicht leer sein"
            return false
        } else if (passwordConf != password) {
            et_password_confirmation.error = "Passwort-Bestätigung muss dem Passwort entsprechen"
            return false
        }
        return true
    }

}