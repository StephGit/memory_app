package ch.stephgit.memory.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ch.stephgit.memory.R
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.ui.main.MainActivity
import com.google.common.base.Optional
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class OnboardingActivity: AppCompatActivity(), OnboardingFlow {

    @Inject
    lateinit var currentUser: Optional<FirebaseUser>

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, OnboardingActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.appComponent.inject(this)
        setContentView(R.layout.activity_onboarding)
        supportActionBar?.title = "Memory"
        invalidateOptionsMenu()

        if (currentUser.isPresent) startActivity(MainActivity.newIntent(this))

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