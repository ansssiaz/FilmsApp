package com.sequenia.filmsapp.feature.films.viewmodel

import com.sequenia.filmsapp.feature.films.api.data.FilmResponse
import com.sequenia.filmsapp.feature.films.data.Film
import com.sequenia.filmsapp.feature.genres.data.Genre

/**
 * Класс используется для преобразования объекта с информацией об одном фильме, полученного с сервера
 * @param filmResponse - объект с информацией о фильме
 */
class FilmMapper {
    fun map(filmResponse: FilmResponse): Film = with(filmResponse) {
        val genreObjects = this.genres.map { Genre(name = it) }
        Film(
            id = id,
            localizedName = localizedName,
            name = name,
            year = year,
            rating = rating,
            imageUrl = imageUrl,
            description = description,
            genres = genreObjects,
        )
    }
}
