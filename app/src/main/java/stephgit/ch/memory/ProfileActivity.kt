package stephgit.ch.memory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

class ProfileActivity: AppCompatActivity()  {
    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, ProfileActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_profile)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.onboarding_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            startActivity(MainActivity.newIntent(this))
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}