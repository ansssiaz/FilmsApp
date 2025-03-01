package com.sequenia.moviesapp.feature.films.api

import com.sequenia.moviesapp.api.RetrofitFactory
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