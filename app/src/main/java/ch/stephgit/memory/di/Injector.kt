package ch.stephgit.memory.di

import android.app.Application
import ch.stephgit.memory.di.DaggerAppComponent.*


object Injector {
    lateinit var appComponent : AppComponent
    fun init(application: Application) {
        appComponent = builder()
            .application(application)
            .build()
    }
}