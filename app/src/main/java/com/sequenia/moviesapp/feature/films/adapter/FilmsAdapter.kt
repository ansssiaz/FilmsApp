package com.sequenia.moviesapp.feature.films.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sequenia.moviesapp.databinding.CardFilmBinding
import com.sequenia.moviesapp.feature.films.data.Film

class FilmsAdapter(
    private val listener: FilmListener,
) : ListAdapter<Film, FilmViewHolder>(FilmDiffCallback()) {

    interface FilmListener {
        fun onFilmImageClicked(film: Film)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardFilmBinding.inflate(layoutInflater, parent, false)
        val viewHolder = FilmViewHolder(binding)
       binding.filmImage.setOnClickListener {
            listener.onFilmImageClicked(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}