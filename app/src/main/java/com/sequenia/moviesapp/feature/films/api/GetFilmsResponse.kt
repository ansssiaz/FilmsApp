package com.sequenia.moviesapp.feature.films.api

import com.sequenia.moviesapp.feature.films.data.Film
import kotlinx.serialization.Serializable

@Serializable
data class GetFilmsResponse(val films: List<Film>)
