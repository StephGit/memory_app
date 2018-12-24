package ch.stephgit.memory

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_onboarding.*
import ch.stephgit.memory.persistence.entity.Player
import ch.stephgit.memory.persistence.repository.PlayerRepository


class OnboardingActivity: AppCompatActivity(), OnboardingFlow {

    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setSupportActionBar(toolbar)
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

    override fun goToAgb(player: Player) {
        this.player = player
        replaceFragement(AGBFragment.newFragment())
    }

    override fun goToProfile() {
        replaceFragement(ProfileFragment.newFragment())
    }

    override fun finishOnboarding() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putString("KEY_TOKEN", "someToken")
            .apply()

        val playerRepository = PlayerRepository(this.applicationContext)
        playerRepository.saveUser(player)

        startActivity(MainActivity.newIntent(this))
    }

}