package com.sequenia.filmsapp.di

import com.sequenia.filmsapp.feature.films.viewmodel.FilmViewModel
import com.sequenia.filmsapp.feature.toolbar.viewmodel.ToolbarViewModel
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