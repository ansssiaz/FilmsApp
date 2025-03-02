package com.sequenia.filmsapp.feature.films.api.data

import kotlinx.serialization.Serializable

@Serializable
data class GetFilmsResponse(val films: List<FilmResponse>)
