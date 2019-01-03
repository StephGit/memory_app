package ch.stephgit.memory

import android.arch.lifecycle.LiveData
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import ch.stephgit.memory.persistence.repository.GameRepository


class HistoryFragment: Fragment() {

    private lateinit var historyItems: LiveData<MutableList<GameListItem>>

    companion object {
        fun newFragment(): Fragment = HistoryFragment()
    }

//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        activity?.title= "History"
        loadHistoryItems()

        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val lvHistory = view.findViewById<ListView>(R.id.lv_history)
        val customAdapter = GameAdapter(requireContext(), 0, historyItems.value!!)
        lvHistory.adapter = customAdapter
//        list.setOnItemClickListener { parent, view, position, id ->
//            someActionWithItem(customAdapter.getItem(position))


        return view
    }

    private fun loadHistoryItems() {
        val app = (requireActivity().application as MemoryApp)
        val username = app.getCurrentUser()!!.displayName
        historyItems = app.getGameRepository().loadHistory(username!!)
    }
}