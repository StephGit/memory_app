package stephgit.ch.memory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {


    private var counter = 0
    private lateinit var gameBoard: LinearLayout
    private lateinit var tvScore: TextView
    private var checkInstance: String = "No Bundle available"
    private var tmpInstanceState: Bundle? = null

    private var cards = mutableMapOf<Button, String>()


    private var firstCard: Button? = null
    private var secondCard: Button? = null
    private var matchedCards = mutableListOf<Button>()

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvScore = findViewById(R.id.tv_score)
        gameBoard = findViewById(R.id.game_play_board)
        val emoijs = mutableListOf("🐵","🐶","🐱","🐻","🦁","🐮","🐨","🐷","🐸","🐔","🦆","🦅","🦉","🦄","🐙","🐘","🦍","🦓")

        val tmpCards = mutableListOf<Button>()
        findButtons(tmpCards, findViewById(R.id.game_board))

        for ( i in 0  until tmpCards.size / 2) {
            val emoji = emoijs.removeAt(Random().nextInt(emoijs.size))
            cards[tmpCards.removeAt(Random().nextInt(tmpCards.size))] = emoji
            cards[tmpCards.removeAt(Random().nextInt(tmpCards.size))] = emoji
        }

        if(savedInstanceState != null) {
            checkInstance = "Bundle available"
            tmpInstanceState = savedInstanceState
        }

        Log.d("Lifecycle onCreate", checkInstance)
    }

    private fun findButtons(tmpCards: MutableList<Button>, parentLayout: LinearLayout) {
        for (i in 0 until parentLayout.childCount) {
            val child = parentLayout.getChildAt(i)
            if (child is Button) {
                tmpCards.add(child)
            } else if (child is LinearLayout)
                findButtons(tmpCards, child)
        }
    }


    fun flipCard(view: View) {
        val button = view as Button

        if (button.text.toString().isBlank()) {

            if (firstCard != null && secondCard != null) {
                flipDown(firstCard!!)
                flipDown(secondCard!!)
                firstCard = null
                secondCard = null
            }

            flipUp(button)

            if (firstCard == null) {
                firstCard = button
            } else if (secondCard == null) {
                secondCard = button
                validateCards()
            }

        } else {
            flipDown(button)
        }
    }

    private fun flipDown(button: Button) {
        if (!matchedCards.contains(button)) {
            button.text = ""
            button.setBackgroundColor(resources.getColor(android.R.color.holo_green_dark))
        }
    }

    private fun flipUp(button: Button) {
        button.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        button.text = cards[button]
    }

    private fun validateCards() {
        if(firstCard!!.text == secondCard!!.text) {
            matchedCards.add(firstCard!!)
            matchedCards.add(secondCard!!)
            tvScore.text = "Score: ${++counter}"
            validateGame()
        }
    }

    private fun validateGame() {
        if (matchedCards.count() == cards.count()) {
            // TODO move to fragments



        }
    }


}
