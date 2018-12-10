package stephgit.ch.memory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_agb.*

class AGBActivity: AppCompatActivity() {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, AGBActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agb)

        val agbText = resources.openRawResource(R.raw.agb).bufferedReader().use {
            it.readText()
        }

        tv_agb.text = Html.fromHtml(agbText)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.onboarding_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            startActivity(ProfileActivity.newIntent(this))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}