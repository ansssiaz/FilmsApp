package com.ansssiaz.filmsapp.di

import com.ansssiaz.filmsapp.feature.films.viewmodel.FilmViewModel
import com.ansssiaz.filmsapp.feature.toolbar.viewmodel.ToolbarViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        FilmViewModel(get())
    }
    viewModel {
        ToolbarViewModel()
    }
}