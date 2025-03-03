package com.sequenia.filmsapp.feature.films.api

import com.sequenia.filmsapp.api.RetrofitFactory
import com.sequenia.filmsapp.feature.films.api.data.GetFilmsResponse
import retrofit2.create
import retrofit2.http.GET

interface FilmsApi {
    @GET("films.json")
    suspend fun getFilms(): GetFilmsResponse

    companion object {
        val INSTANCE: FilmsApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}