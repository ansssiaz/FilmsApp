package com.sequenia.filmsapp.feature.films.repository

import com.sequenia.filmsapp.feature.films.api.data.FilmResponse

interface FilmsRepository {
    suspend fun getFilmsInformation(): List<FilmResponse>
}