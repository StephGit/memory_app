package ch.stephgit.memory.onboarding

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ch.stephgit.memory.MainActivity
import ch.stephgit.memory.MemoryApp
import ch.stephgit.memory.R
import ch.stephgit.memory.persistence.entity.Player
import java.util.*


class OnboardingActivity: AppCompatActivity(), OnboardingFlow {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
//        setSupportActionBar(toolbar)
        supportActionBar?.title = "Memory"
        invalidateOptionsMenu()

        if (PreferenceManager.getDefaultSharedPreferences(this).getString("KEY_TOKEN", null) != null) {
            startActivity(MainActivity.newIntent(this))
        }

        if (savedInstanceState == null) {
            replaceFragement(OnboardingFragment.newFragment())
        }
    }

    private fun replaceFragement(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.onboarding, fragment)
            .commit()
    }

    override fun goToLogin() {
        replaceFragement(LoginFragment.newFragment())
    }

    override fun goToRegistration() {
        replaceFragement(RegistrationFragment.newFragment())
    }

    override fun goToAgb() {
        replaceFragement(AGBFragment.newFragment())
    }

    override fun goToProfile() {
        replaceFragement(ProfileFragment.newFragment())
    }

    override fun finishOnboarding() {
        val player = (this.application as MemoryApp).getCurrentPlayer()
        val userId = (this.application as MemoryApp).getPlayerRepository().saveUser(player)

        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putString("KEY_TOKEN", Date().toString() + ":" + userId )
            .apply()

        startActivity(MainActivity.newIntent(this))
    }

}