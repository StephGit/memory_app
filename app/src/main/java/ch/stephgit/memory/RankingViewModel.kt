package ch.stephgit.memory

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ch.stephgit.memory.persistence.entity.Game

class RankingViewModel constructor(application: Application): ViewModel() {

    val gameItem: MutableLiveData<List<Game>>

    init {
        gameItem = (application as MemoryApp).getGameRepository().loadRanking()
    }



}