package com.sequenia.filmsapp.feature.films.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sequenia.filmsapp.feature.films.data.Film
import com.sequenia.filmsapp.feature.films.repository.FilmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.sequenia.filmsapp.util.Status

class FilmViewModel(
    private val repository: FilmsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(FilmUiState())
    val state = _state.asStateFlow()

    init {
        getFilms()
    }

    fun getFilms() {
        _state.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val films: List<Film> = repository.getFilmsInformation()
                _state.update {
                    it.copy(
                        films = films.sortedBy { film -> film.localizedName },
                        status = Status.Idle
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }
}