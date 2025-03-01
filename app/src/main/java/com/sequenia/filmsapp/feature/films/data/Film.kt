package com.sequenia.filmsapp.feature.films.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Film(
    val id: Long = 0L,
    @SerialName("localized_name")
    val localizedName: String = "",
    val name: String = "",
    val year: Int = 0,
    val rating: Double? = 0.0,
    @SerialName("image_url")
    val imageUrl: String? = "",
    val description: String? = "",
    val genres: List<String> = emptyList(),
)
