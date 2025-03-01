package com.sequenia.filmsapp.feature.films.repository

import com.sequenia.filmsapp.feature.films.data.Film

interface FilmsRepository {
    suspend fun getFilmsInformation(): List<Film>
}