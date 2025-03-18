package com.ansssiaz.filmsapp.di

import com.ansssiaz.filmsapp.feature.films.repository.FilmsRepository
import com.ansssiaz.filmsapp.feature.films.repository.NetworkFilmsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideFilmsRepository(get()) }

    single<FilmsRepository> {
        return@single NetworkFilmsRepository(get())
    }
}

private fun provideFilmsRepository(repository: NetworkFilmsRepository): FilmsRepository = repository