package com.ansssiaz.filmsapp.feature.genres.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ansssiaz.filmsapp.feature.genres.data.Genre

class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem == newItem
}