package com.ansssiaz.filmsapp.feature.genres.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ansssiaz.filmsapp.databinding.CardGenreBinding
import com.ansssiaz.filmsapp.feature.genres.data.Genre

class GenresAdapter(
    private val listener: GenreListener,
    private var selectedGenreId: Long? = null
) : ListAdapter<Genre, GenreViewHolder>(GenreDiffCallback()) {

    interface GenreListener {
        fun onGenreClicked(genre: Genre)
        fun onGenreDeselected()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardGenreBinding.inflate(layoutInflater, parent, false)
        return GenreViewHolder(binding)
    }

    /**
     * Функция используется для связи объекта Genre из списка с ViewHolder.
     * При этом проверяет, выбран ли текущий жанр и обновляет отображение.
     * Если жанр уже выбран, она снимает выбор, если выбран новый жанр,
     * она обновляет переменную selectedGenreId, а также уведомляет слушателя о клике.
     * @param holder - объект GenreViewHolder
     * @param position - индекс жанра в списке
     */
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

    /**
     * Функция обновляет переменную selectedGenreId на id нового выбранного жанра, полученный из интерфейса.
     * @param newSelectedGenreId - id нового жанра
     */
    @SuppressLint("NotifyDataSetChanged")
    fun updateSelectedGenreId(newSelectedGenreId: Long?) {
        selectedGenreId = newSelectedGenreId
        notifyDataSetChanged()
    }
}