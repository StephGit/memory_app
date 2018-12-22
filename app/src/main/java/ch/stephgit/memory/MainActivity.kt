package ch.stephgit.memory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ch.stephgit.memory.R

class MainActivity : AppCompatActivity(), GamePlayFlow {


    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main, GamePlayFragment())
                .commit()
//        }
//        else {


//        val items =
//            mutableListOf(
//                ScoreListItem("Hans", Date(), "12")
//        ,ScoreListItem("Peter", Date(), "10"))
//        val customAdapter = ScoreAdapter(this, 0, items)
//        lv_history.adapter = customAdapter
////        list.setOnItemClickListener { parent, view, position, id ->
////            someActionWithItem(customAdapter.getItem(position))
////        }

    }

    override fun goToResult(score: String) {
        var bundle = Bundle()
        bundle.putString("score", score)
        var fragment = OverlayMessageFragment()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main, fragment)
            .commit()
    }

}
