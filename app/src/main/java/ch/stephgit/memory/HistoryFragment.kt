package ch.stephgit.memory

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import java.util.*

class HistoryFragment: Fragment() {

    private lateinit var historyItems: MutableList<ScoreListItem>

    companion object {
        fun newFragment(): Fragment = RankingFragment()
    }

//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        loadHistoryItems()

        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val lv_history = view.findViewById<ListView>(R.id.lv_history)
        val customAdapter = ScoreAdapter(requireContext(), 0, historyItems)
        lv_history.adapter = customAdapter
//        list.setOnItemClickListener { parent, view, position, id ->
//            someActionWithItem(customAdapter.getItem(position))

        return view
    }

    private fun loadHistoryItems() {
        historyItems = mutableListOf(
            ScoreListItem("Hans", Date(), "12")
            ,ScoreListItem("Peter", Date(), "10"))
    }
}