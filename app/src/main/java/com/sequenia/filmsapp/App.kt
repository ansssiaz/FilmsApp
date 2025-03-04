package com.sequenia.filmsapp

import android.app.Application
import com.sequenia.filmsapp.di.apiModule
import com.sequenia.filmsapp.di.repositoryModule
import com.sequenia.filmsapp.di.viewModelModule
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