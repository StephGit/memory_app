package ch.stephgit.memory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ch.stephgit.memory.persistence.entity.Player

class MainActivity : AppCompatActivity(), GamePlayFlow {

    private lateinit var navigationView: NavigationView
    private lateinit var drawLayout: DrawerLayout
    private lateinit var player: Player

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                }
            }
            drawLayout.closeDrawer(GravityCompat.START)
            true
        }

        if (PreferenceManager.getDefaultSharedPreferences(this).getString("KEY_TOKEN", null) != null) {
            val token = PreferenceManager.getDefaultSharedPreferences(this).getString("KEY_TOKEN", null)
            val id: Int? = token?.substringAfterLast(":")?.toInt()
            if (id != null) {
                player = (application as MemoryApp).getPlayerRepository().findPlayerById(id)
                (application as MemoryApp).setCurrentPlayer(player)
            }
        }


        if (savedInstanceState == null) {
            replaceFragment(GamePlayFragment())
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

    override fun goToResult(flips: Int) {

        val bundle = Bundle()
        bundle.putInt("flips", flips)
        val fragment = OverlayMessageFragment()
        fragment.arguments = bundle
        replaceFragment(fragment)
    }

}
