package com.ansssiaz.filmsapp.feature.films.data

import com.ansssiaz.filmsapp.feature.genres.data.Genre

data class Film(
    val id: Long = 0L,
    val localizedName: String = "",
    val name: String = "",
    val year: Int = 0,
    val rating: Double? = null,
    val imageUrl: String? = null,
    val description: String? = "",
    val genres: List<Genre> = emptyList(),
)
