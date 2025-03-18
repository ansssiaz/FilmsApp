package com.ansssiaz.filmsapp.feature.films.api.data

import kotlinx.serialization.Serializable

/**
 * Класс для получения данных о всех фильмах с сервера
 * @param films - список объектов FilmResponse
 */
@Serializable
data class GetFilmsResponse(val films: List<FilmResponse>)
