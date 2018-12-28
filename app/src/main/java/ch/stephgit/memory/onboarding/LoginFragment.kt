package ch.stephgit.memory.onboarding

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.*
import android.widget.EditText
import ch.stephgit.memory.MainActivity
import ch.stephgit.memory.MemoryApp
import ch.stephgit.memory.R
import ch.stephgit.memory.persistence.entity.Player
import kotlinx.android.synthetic.main.fragment_registration.*
import java.util.*

class LoginFragment : Fragment() {

    private var player: Player? = null

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    companion object {
        fun newFragment(): Fragment = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        etUsername = view.findViewById(R.id.et_username)
        etPassword = view.findViewById(R.id.et_password)

        return view
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
                    .putString("KEY_TOKEN", Date().toString() + ":" + player?.id )
                    .apply()
                startActivity(MainActivity.newIntent(requireContext()))
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isValid(): Boolean {
        val username: String = etUsername.text.toString().trim()
        val password: String = etPassword.text.toString().trim()

        player = (requireActivity().application as MemoryApp).getPlayerRepository().findPlayerByUserName(username)

        if (player?.password != password) {
            etUsername.error = "Wrong username or password"
            etPassword.error = "Wrong username or password"
            return false
        }
        return true
    }

}
