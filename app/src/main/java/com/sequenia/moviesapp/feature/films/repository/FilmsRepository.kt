package com.sequenia.moviesapp.feature.films.repository

import com.sequenia.moviesapp.feature.films.data.Film

interface FilmsRepository {
    suspend fun getFilmsInformation(): List<Film>
}