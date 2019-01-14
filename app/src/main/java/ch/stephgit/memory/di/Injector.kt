package ch.stephgit.memory.di

import android.app.Application
import ch.stephgit.memory.AppComponent
import ch.stephgit.memory.DaggerAppComponent


object Injector {
    lateinit var appComponent : AppComponent
    fun init(application: Application) {
        appComponent = DaggerAppComponent
            .builder()
            .application(application)
            .build()
    }
}