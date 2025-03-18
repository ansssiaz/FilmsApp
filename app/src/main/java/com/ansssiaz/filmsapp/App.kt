package com.ansssiaz.filmsapp

import android.app.Application
import com.ansssiaz.filmsapp.di.apiModule
import com.ansssiaz.filmsapp.di.repositoryModule
import com.ansssiaz.filmsapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(apiModule, repositoryModule, viewModelModule))
        }
    }
}