package ch.stephgit.memory

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class RankingFragment: Fragment() {

    private lateinit var rankingItems: MutableLiveData<List<GameListItem>>

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
        loadRankingItems()

        val view = inflater.inflate(R.layout.fragment_ranking, container, false)

        var viewModel: GameViewModel = GameViewModel(rankingItems)
        viewModel.getGameItem().observe(this, Observer {
            val lvRanking = view.findViewById<ListView>(R.id.lv_ranking)
            val customAdapter = GameAdapter(requireContext(), 0, it!!)
            lvRanking.adapter = customAdapter
        })

        return view
    }

    private fun loadRankingItems() {
        rankingItems = (requireActivity().application as MemoryApp).getGameRepository().loadRanking()
    }
}