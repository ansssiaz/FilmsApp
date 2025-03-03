package com.sequenia.filmsapp.feature.genres.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sequenia.filmsapp.R
import com.sequenia.filmsapp.databinding.CardGenreBinding
import com.sequenia.filmsapp.feature.genres.data.Genre
import java.util.Locale

class GenreViewHolder(private val binding: CardGenreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: Genre, isSelected: Boolean, onClick: (Genre) -> Unit) {
        binding.genreName.text = genre.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        binding.cardGenre.setBackgroundColor(
            if (isSelected) {
                ContextCompat.getColor(binding.root.context, R.color.yellow)
            } else {
                ContextCompat.getColor(binding.root.context, android.R.color.transparent)
            }
        )

        binding.root.setOnClickListener {
            onClick(genre)
        }
    }
}