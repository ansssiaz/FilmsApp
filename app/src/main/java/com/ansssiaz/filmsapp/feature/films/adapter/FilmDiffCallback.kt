package com.ansssiaz.filmsapp.feature.films.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ansssiaz.filmsapp.feature.films.data.Film

class FilmDiffCallback: DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem
}