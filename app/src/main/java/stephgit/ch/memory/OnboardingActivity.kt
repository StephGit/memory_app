package stephgit.ch.memory

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity: AppCompatActivity(), OnboardingFlow {


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

    override fun goToAgb() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.onboarding, AGBFragment())
            .commit()
    }

}