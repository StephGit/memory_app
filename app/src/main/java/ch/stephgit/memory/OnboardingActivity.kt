package ch.stephgit.memory

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_onboarding.*
import ch.stephgit.memory.persistence.entity.Player
import ch.stephgit.memory.persistence.repository.PlayerRepository
import ch.stephgit.memory.R


class OnboardingActivity: AppCompatActivity(), OnboardingFlow {

    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setSupportActionBar(toolbar);
        supportActionBar?.title = ""
        invalidateOptionsMenu()

        if (PreferenceManager.getDefaultSharedPreferences(this).getString("KEY_TOKEN", null) != null) {
            startActivity(MainActivity.newIntent(this))
        }


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.onboarding, OnboardingFragment())
                .commit()
        }
    }

    override fun goToRegistration() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.onboarding, RegistrationFragment())
            .commit()
    }

    override fun goToAgb(player: Player) {
        this.player = player
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.onboarding, AGBFragment())
            .commit()
    }

    override fun goToProfile() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.onboarding, ProfileFragment())
            .commit()
    }

    override fun finishOnboarding() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putString("KEY_TOKEN", "someToken")
            .apply()

        val playerRepository: PlayerRepository =
            PlayerRepository(this.applicationContext)
        playerRepository.saveUser(player)

        startActivity(MainActivity.newIntent(this))
    }

}