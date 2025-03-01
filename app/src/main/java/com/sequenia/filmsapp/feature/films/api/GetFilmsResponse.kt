package com.sequenia.filmsapp.feature.films.api

import com.sequenia.filmsapp.feature.films.data.Film
import kotlinx.serialization.Serializable

@Serializable
data class GetFilmsResponse(val films: List<Film>)
