package ch.stephgit.memory.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.persistence.entity.Game
import ch.stephgit.memory.persistence.repository.GameRepository
import javax.inject.Inject

class HistoryViewModel: ViewModel() {

    @Inject
    lateinit var gameRepository: GameRepository



    val gameItem: MutableLiveData<List<Game>>

    init {
        Injector.appComponent.inject(this)
        // TODO user as di
//        if (app .getCurrentUser() != null) {
            gameItem = gameRepository.loadHistory("user")
//            gameItem = gameRepository.loadHistory(app.getCurrentUser()!!.displayName!!)
//        } else {
//            gameItem = AbsentLiveData.create()
//        }
    }



}