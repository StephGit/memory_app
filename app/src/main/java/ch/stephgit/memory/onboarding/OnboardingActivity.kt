package ch.stephgit.memory.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ch.stephgit.memory.MainActivity
import ch.stephgit.memory.R
import com.google.firebase.auth.FirebaseAuth


class OnboardingActivity: AppCompatActivity(), OnboardingFlow {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        supportActionBar?.title = "Memory"
        invalidateOptionsMenu()

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null) {
            startActivity(MainActivity.newIntent(this))
        }

        if (savedInstanceState == null) {
            replaceFragement(LoginFragment.newFragment())
        }
    }

    private fun replaceFragement(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.onboarding, fragment)
            .commit()
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

//        PreferenceManager.getDefaultSharedPreferences(this)
//            .edit()
//            .putString("KEY_TOKEN", Date().toString() + ":" + userId )
//            .apply()

        startActivity(MainActivity.newIntent(this))
    }

}