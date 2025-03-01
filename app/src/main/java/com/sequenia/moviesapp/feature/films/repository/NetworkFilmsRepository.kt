package com.sequenia.moviesapp.feature.films.repository

import com.sequenia.moviesapp.feature.films.data.Film
import com.sequenia.moviesapp.feature.films.api.FilmsApi

class NetworkFilmsRepository(private val api: FilmsApi) : FilmsRepository {
    override suspend fun getFilmsInformation(): List<Film> {
        val response = api.getFilms()
        return response.films
    }
}