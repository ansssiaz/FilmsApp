package com.sequenia.filmsapp.feature.films.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sequenia.filmsapp.feature.films.api.data.FilmResponse
import com.sequenia.filmsapp.feature.films.data.Film
import com.sequenia.filmsapp.feature.films.repository.FilmsRepository
import com.sequenia.filmsapp.feature.genres.data.Genre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.sequenia.filmsapp.util.Status

class FilmViewModel(
    private val repository: FilmsRepository,
    private val mapper: FilmMapper,
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
                val filmResponses: List<FilmResponse> = repository.getFilmsInformation()
                val films = filmResponses.map(mapper::map)

                val uniqueGenres = getAllUniqueGenres(films)

                val updatedFilms = films.map { film ->
                    val updatedGenres = film.genres.map { genre ->
                        uniqueGenres[genre.name] ?: genre
                    }
                    film.copy(genres = updatedGenres)
                }

                _state.update {
                    it.copy(
                        films = updatedFilms.sortedBy { film -> film.localizedName },
                        status = Status.Idle,
                        uniqueGenresList = uniqueGenres.values.toList()
                            .sortedBy { genre -> genre.name }
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    private fun getAllUniqueGenres(films: List<Film>): Map<String, Genre> {
        val mutableUniqueGenres = mutableMapOf<String, Genre>()
        var genreIdCounter = 0L

        films.forEach { film ->
            film.genres.forEach { genre ->
                if (!mutableUniqueGenres.containsKey(genre.name)) {
                    genreIdCounter++
                    mutableUniqueGenres[genre.name] = Genre(genreIdCounter, genre.name)
                }
            }
        }

        val uniqueGenres = mutableUniqueGenres.toMap()
        return uniqueGenres
    }

    fun setSelectedGenre(id: Long?) {
        _state.update { currentState ->
            if (currentState.selectedGenreId == id) {
                currentState.copy(selectedGenreId = null)
            } else {
                currentState.copy(selectedGenreId = id)
            }
        }
    }

    fun getFilteredFilms(): List<Film>? {
        return _state.value.films?.let { films ->
            _state.value.selectedGenreId?.let { selectedId ->
                films.filter { film -> film.genres.any { it.id == selectedId } }
            } ?: films
        }
    }
}