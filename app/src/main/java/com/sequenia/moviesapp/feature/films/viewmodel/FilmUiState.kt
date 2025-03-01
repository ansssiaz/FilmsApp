package com.sequenia.moviesapp.feature.films.viewmodel

import com.sequenia.moviesapp.feature.films.data.Film
import com.sequenia.moviesapp.util.Status

data class FilmUiState(
    val films: List<Film>? = null,
    val status: Status = Status.Idle,
) {
    val isEmptyLoading: Boolean = status == Status.Loading && films.isNullOrEmpty()
    val isError: Boolean
        get() = status is Status.Error && films.isNullOrEmpty()
}