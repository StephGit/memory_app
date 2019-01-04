package ch.stephgit.memory

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class GameViewModel(private var gameItem: MutableLiveData<List<GameListItem>>) : ViewModel() {

    fun getGameItem() = gameItem

}