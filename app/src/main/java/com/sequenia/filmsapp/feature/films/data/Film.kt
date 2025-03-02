package com.sequenia.filmsapp.feature.films.data

import com.sequenia.filmsapp.feature.genres.data.Genre

data class Film(
    val id: Long = 0L,
    val localizedName: String = "",
    val name: String = "",
    val year: Int = 0,
    val rating: Double? = 0.0,
    val imageUrl: String? = "",
    val description: String? = "",
    val genres: List<Genre> = emptyList(),
)
