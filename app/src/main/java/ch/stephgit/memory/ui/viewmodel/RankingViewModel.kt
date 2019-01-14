package ch.stephgit.memory.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.persistence.entity.Game
import ch.stephgit.memory.persistence.repository.GameRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RankingViewModel: ViewModel() {

    @Inject
    lateinit var gameRepository: GameRepository

    val gameItem: MutableLiveData<List<Game>>

    init {
        Injector.appComponent.inject(this)
        gameItem = gameRepository.loadRanking()

    }
}