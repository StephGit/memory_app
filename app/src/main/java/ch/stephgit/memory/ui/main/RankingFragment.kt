package ch.stephgit.memory.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import ch.stephgit.memory.GameAdapter
import ch.stephgit.memory.R
import ch.stephgit.memory.RankingViewModel

class RankingFragment: Fragment() {

    private lateinit var viewModel: RankingViewModel

    companion object {
        fun newFragment(): Fragment = RankingFragment()
    }

//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        activity?.title= "Ranking"

        val view = inflater.inflate(R.layout.fragment_ranking, container, false)

//        val factory = ViewModelProvider.Factory
//        val viewModel: RankingViewModel = ViewModelProvider(this, factory).get(RankingViewModel::class.java)

        viewModel = RankingViewModel(activity!!.application)
        viewModel.gameItem.observe(this, Observer {
            val lvRanking = view.findViewById<ListView>(R.id.lv_ranking)
            val customAdapter = GameAdapter(requireContext(), 0, it!!)
            lvRanking.adapter = customAdapter
        })

        return view
    }
}