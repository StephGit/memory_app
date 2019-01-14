package ch.stephgit.memory.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import ch.stephgit.memory.R
import ch.stephgit.memory.ui.viewmodel.HistoryViewModel
import ch.stephgit.memory.ui.viewmodel.ViewModelFactory


class HistoryFragment: Fragment() {

    companion object {
        fun newFragment(): Fragment = HistoryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        activity?.title= "History"

        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val factory = ViewModelFactory()
        val viewModel: HistoryViewModel = ViewModelProviders.of(this, factory).get(HistoryViewModel::class.java)

        viewModel.gameItem.observe(this, Observer {
            val lvHistory = view.findViewById<ListView>(R.id.lv_history)
            val customAdapter = GameAdapter(requireContext(), 0, it!!)
            lvHistory.adapter = customAdapter
        })

        return view
    }
}