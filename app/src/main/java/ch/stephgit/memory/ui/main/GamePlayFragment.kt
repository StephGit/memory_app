package ch.stephgit.memory.ui.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import ch.stephgit.memory.R
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.persistence.entity.Game
import ch.stephgit.memory.persistence.repository.GameRepository
import com.google.firebase.auth.FirebaseUser
import java.util.*
import javax.inject.Inject

class GamePlayFragment: Fragment(), View.OnClickListener {

    @Inject
    lateinit var gameRepository: GameRepository

    @Inject
    lateinit var currentUser: Optional<FirebaseUser>

    private lateinit var tvFlips: TextView
    private var cards = mutableMapOf<Button, String>()
    private var counter: Long = 0
    private var firstCard: Button? = null
    private var secondCard: Button? = null
    private var matchedCards = mutableListOf<Button>()

    private lateinit var callback: GamePlayFlow

    companion object {
        fun newFragment(): Fragment = GamePlayFragment()
    }

    override fun onAttach(context: Context?) {
        Injector.appComponent.inject(this)
        super.onAttach(context)
        callback = context as? GamePlayFlow ?: throw RuntimeException("Missing GamePlayFlow implementation")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = "Memory"

        val view = inflater.inflate(R.layout.fragment_game_play_board, container, false)
        tvFlips = view.findViewById(R.id.tv_flips)

        val emoijs = mutableListOf("\uD83D\uDCA9","\uD83E\uDD21","\uD83D\uDC79","\uD83D\uDC7A","\uD83D\uDC7B","\uD83D\uDC7D","\uD83D\uDC7E","\uD83E\uDD16","\uD83D\uDC27","\uD83C\uDF83","☢","☣","\uD83C\uDF7A","\uD83E\uDD43","☠","\uD83D\uDCA3","\uD83D\uDD95","\uD83E\uDDB7")

        val tmpCards = mutableListOf<Button>()
        findButtons(tmpCards, view.findViewById(R.id.game_play_board))

        for (i in 0 until tmpCards.size / 2) {
            val emoji = emoijs.removeAt(Random().nextInt(emoijs.size))
            cards[tmpCards.removeAt(Random().nextInt(tmpCards.size))] = emoji
            cards[tmpCards.removeAt(Random().nextInt(tmpCards.size))] = emoji
        }

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

    private fun flipCard(view: View) {
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
            button.background = resources.getDrawable(R.drawable.ic_card, null)
        }
    }

    private fun flipUp(button: Button) {
        button.text = cards[button]
        button.background = resources.getDrawable(R.drawable.ic_flipcard, null)
        tvFlips.text = "Flips: ${++counter}"
    }

    private fun validateCards() {
        if(firstCard!!.text == secondCard!!.text) {
            matchedCards.add(firstCard!!)
            matchedCards.add(secondCard!!)

            validateGame()
        }
    }

    private fun validateGame() {
        if (matchedCards.count() == cards.count()) {
            gameRepository.saveGame(Game(currentUser.get().displayName.toString(), Date(), counter))
            callback.goToResult(counter)
        }
    }
}