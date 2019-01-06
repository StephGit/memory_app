package ch.stephgit.memory

import android.arch.lifecycle.MutableLiveData

class AbsentLiveData<T> private constructor() : MutableLiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): MutableLiveData<T> {
            return  AbsentLiveData()
        }
    }
}