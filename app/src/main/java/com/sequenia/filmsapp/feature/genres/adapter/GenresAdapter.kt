package com.sequenia.filmsapp.feature.genres.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sequenia.filmsapp.databinding.CardGenreBinding
import com.sequenia.filmsapp.feature.genres.data.Genre

class GenresAdapter(
    private val listener: GenreListener,
) : ListAdapter<Genre, GenreViewHolder>(GenreDiffCallback()) {

    private var selectedGenreId: Long? = null

    interface GenreListener {
        fun onGenreClicked(genre: Genre)
        fun onGenreDeselected()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardGenreBinding.inflate(layoutInflater, parent, false)
        return GenreViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre, genre.id == selectedGenreId) { selectedGenre ->
            val isSameGenre = selectedGenreId == selectedGenre.id
            if (isSameGenre) {
                selectedGenreId = null
                listener.onGenreDeselected()
            } else {
                selectedGenreId = selectedGenre.id
                listener.onGenreClicked(selectedGenre)
            }
            notifyDataSetChanged()
        }
    }
}