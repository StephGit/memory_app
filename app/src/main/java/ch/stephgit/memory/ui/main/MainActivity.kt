package ch.stephgit.memory.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ch.stephgit.memory.R
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.persistence.repository.UserRepository
import ch.stephgit.memory.ui.onboarding.OnboardingActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity(), GamePlayFlow {

    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var navigationView: NavigationView
    private lateinit var drawLayout: DrawerLayout

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.appComponent.inject(this)
        if (userRepository.getCurrentUser() != null) {

            setContentView(R.layout.activity_main)

            drawLayout = findViewById(R.id.draw_layout)

            val actionbar = supportActionBar!!
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)

            navigationView = findViewById(R.id.nav_view)
            navigationView.setNavigationItemSelectedListener {

                if (!it.isChecked) {
                    it.isChecked = true
                    when (it.itemId) {
                        R.id.nav_action_game -> replaceFragment(GamePlayFragment.newFragment())
                        R.id.nav_action_history -> replaceFragment(HistoryFragment.newFragment())
                        R.id.nav_action_ranking -> replaceFragment(RankingFragment.newFragment())
                        R.id.nav_action_profile -> replaceFragment(UserFragment.newFragment())
                        R.id.nav_action_logout -> {
                            userRepository.logout()
                            startActivity(OnboardingActivity.newIntent(this))
                        }
                    }
                }
                drawLayout.closeDrawer(GravityCompat.START)
                true
            }

            if (savedInstanceState == null) {
                replaceFragment(GamePlayFragment())
            }
        } else {
            startActivity(OnboardingActivity.newIntent(this))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (drawLayout.isDrawerOpen(GravityCompat.START)) {
                    drawLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main, fragment)
            .commit()
    }

    override fun goToResult(flips: Long) {

        val bundle = Bundle()
        bundle.putLong("flips", flips)
        val fragment = OverlayMessageFragment()
        fragment.arguments = bundle
        replaceFragment(fragment)
    }

}
