package com.ansssiaz.filmsapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ansssiaz.filmsapp.BuildConfig
import com.ansssiaz.filmsapp.feature.films.api.FilmsApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideFilmsApi(get()) }
}

private val contentType = "application/json".toMediaType()
private val json = Json { ignoreUnknownKeys = true }

private fun provideOkHttpClient() = OkHttpClient.Builder()
    .addInterceptor {
        it.proceed(
            it.request()
                .newBuilder()
                .build()
        )
    }
    .let {
        if (BuildConfig.DEBUG) {
            it.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        } else {
            it
        }
    }
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/")
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

private fun provideFilmsApi(retrofit: Retrofit): FilmsApi = retrofit.create(FilmsApi::class.java)