package com.sequenia.filmsapp.feature.films.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sequenia.filmsapp.databinding.CardFilmBinding
import com.sequenia.filmsapp.feature.films.data.Film
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sequenia.filmsapp.R

class FilmViewHolder(private val binding: CardFilmBinding) : RecyclerView.ViewHolder(binding.root) {
    private val radius =
        this.itemView.context.resources.getDimensionPixelSize(R.dimen.corner_radius)

    fun bind(film: Film) {
        binding.filmName.text = film.localizedName
        if (film.imageUrl.isNullOrEmpty()) {
            Glide.with(binding.root)
                .load(R.drawable.empty_film_image)
                .into(binding.filmImage)
        } else {
            Glide.with(binding.root)
                .load(film.imageUrl)
                .transform(RoundedCorners(radius))
                .placeholder(R.drawable.empty_film_image)
                .error(R.drawable.empty_film_image)
                .into(binding.filmImage)
        }
    }
}