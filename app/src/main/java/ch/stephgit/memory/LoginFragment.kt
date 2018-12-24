package ch.stephgit.memory

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.*
import ch.stephgit.memory.persistence.entity.Player
import ch.stephgit.memory.persistence.repository.PlayerRepository
import kotlinx.android.synthetic.main.fragment_registration.*

class LoginFragment : Fragment() {

    private var player: Player? = null

    companion object {
        fun newFragment(): Fragment = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        val menuItem = menu?.findItem(R.id.action_next)
        menuItem?.title = "Login"
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            if (isValid()) {
                PreferenceManager.getDefaultSharedPreferences(requireContext())
                    .edit()
                    .putString("KEY_TOKEN", "someToken")
                    .apply()
                startActivity(MainActivity.newIntent(requireContext()))
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isValid(): Boolean {
        val username: String = et_username.text.toString().trim()
        val password: String = et_password.text.toString().trim()

        val playerRepository = PlayerRepository(requireContext().applicationContext)
        player = playerRepository.findPlayerByUserName(username)

        if (player?.password != password) {
            et_username.error = "Wrong username or password"
            et_password.error = "Wrong username or password"
            return false
        }
        return true
    }

}
