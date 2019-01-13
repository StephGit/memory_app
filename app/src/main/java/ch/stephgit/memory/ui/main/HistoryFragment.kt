package ch.stephgit.memory.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import ch.stephgit.memory.GameAdapter
import ch.stephgit.memory.HistoryViewModel
import ch.stephgit.memory.R


class HistoryFragment: Fragment() {

    private lateinit var viewModel: HistoryViewModel

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

        val view = inflater.inflate(R.layout.fragment_history, container, false)


        viewModel = HistoryViewModel(requireActivity().application)
        viewModel.gameItem.observe(this, Observer {
            val lvHistory = view.findViewById<ListView>(R.id.lv_history)
            val customAdapter = GameAdapter(requireContext(), 0, it!!)
            lvHistory.adapter = customAdapter
        })

        return view
    }
}