package com.ansssiaz.filmsapp.feature.films.repository

import com.ansssiaz.filmsapp.feature.films.api.data.FilmResponse

interface FilmsRepository {
    suspend fun getFilmsInformation(): List<FilmResponse>
}