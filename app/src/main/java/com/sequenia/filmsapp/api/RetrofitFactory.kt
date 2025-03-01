package com.sequenia.filmsapp.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitFactory {
    private val contentType = "application/json".toMediaType()
    private val json = Json { ignoreUnknownKeys = true }
    val INSTANCE: Retrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClientFactory.INSTANCE)
            .baseUrl("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}