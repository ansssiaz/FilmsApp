package com.ansssiaz.filmsapp.feature.films.repository

import com.ansssiaz.filmsapp.feature.films.api.data.FilmResponse
import com.ansssiaz.filmsapp.feature.films.api.FilmsApi

class NetworkFilmsRepository(private val api: FilmsApi) : FilmsRepository {
    override suspend fun getFilmsInformation(): List<FilmResponse> {
        val response = api.getFilms()
        return response.films
    }
}