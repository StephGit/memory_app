package ch.stephgit.memory

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ch.stephgit.memory.persistence.entity.Game

class HistoryViewModel constructor(application: Application): ViewModel() {

    val gameItem: MutableLiveData<List<Game>>

    init {
        val app = (application as MemoryApp)
        if (app.getCurrentUser() != null) {
            gameItem = app.getGameRepository().loadHistory(app.getCurrentUser()!!.displayName!!)
        } else {
            gameItem = AbsentLiveData.create()
        }
    }



}