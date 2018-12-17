package stephgit.ch.memory

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

class GamePlayFragment: Fragment(), View.OnClickListener {


    private lateinit var tvScore: TextView
    private var cards = mutableMapOf<Button, String>()
    private var counter = 0
    private var firstCard: Button? = null
    private var secondCard: Button? = null
    private var matchedCards = mutableListOf<Button>()


    private lateinit var callback: GamePlayFlow

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? GamePlayFlow ?: throw RuntimeException("Missing GamePlayFlow implementation")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        if (savedInstanceState == null) {
            val view = inflater.inflate(R.layout.fragment_game_play_board, container, false)
            tvScore = view.findViewById(R.id.tv_score)

            val emoijs = mutableListOf("🐵","🐶","🐱","🐻","🦁","🐮","🐨","🐷","🐸","🐔","🦆","🦅","🦉","🦄","🐙","🐘","🦍","🦓")

            val tmpCards = mutableListOf<Button>()
            findButtons(tmpCards, view.findViewById(R.id.game_play_board))

            for ( i in 0  until tmpCards.size / 2) {
                val emoji = emoijs.removeAt(Random().nextInt(emoijs.size))
                cards[tmpCards.removeAt(Random().nextInt(tmpCards.size))] = emoji
                cards[tmpCards.removeAt(Random().nextInt(tmpCards.size))] = emoji
            }
//        }

        return view
    }

    private fun findButtons(tmpCards: MutableList<Button>, parentLayout: LinearLayout) {
        for (i in 0 until parentLayout.childCount) {
            val child = parentLayout.getChildAt(i)
            if (child is Button) {
                tmpCards.add(child)
                child.setOnClickListener(this)
            } else if (child is LinearLayout)
                findButtons(tmpCards, child)
        }
    }

    override fun onClick(v: View) {
        flipCard(v)
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
            button.setBackgroundColor(ResourcesCompat.getColor(resources, android.R.color.holo_green_light, null))

        }
    }

    private fun flipUp(button: Button) {
        button.text = cards[button]
        button.setBackgroundColor(ResourcesCompat.getColor(resources, android.R.color.holo_blue_bright, null))
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
        if (matchedCards.count() != cards.count()) {
            callback.goToResult(tvScore.text.toString())
        }
    }
}