package com.sequenia.filmsapp.di

import com.sequenia.filmsapp.feature.films.repository.FilmsRepository
import com.sequenia.filmsapp.feature.films.repository.NetworkFilmsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideFilmsRepository(get()) }

    single<FilmsRepository> {
        return@single NetworkFilmsRepository(get())
    }
}

private fun provideFilmsRepository(repository: NetworkFilmsRepository): FilmsRepository = repository