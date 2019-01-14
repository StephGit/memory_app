package ch.stephgit.memory.di

import android.app.Application
import ch.stephgit.memory.AppComponent


object Injector {
    lateinit var appComponent : AppComponent
    fun init(application: Application) {
        appComponent = AppComponent
            .Builder
            .application(application)
            .build()
    }
}