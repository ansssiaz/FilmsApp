package com.sequenia.filmsapp.feature.films.repository

import com.sequenia.filmsapp.feature.films.data.Film
import com.sequenia.filmsapp.feature.films.api.FilmsApi

class NetworkFilmsRepository(private val api: FilmsApi) : FilmsRepository {
    override suspend fun getFilmsInformation(): List<Film> {
        val response = api.getFilms()
        return response.films
    }
}