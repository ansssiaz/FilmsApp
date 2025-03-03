package com.sequenia.filmsapp.feature.filmcard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sequenia.filmsapp.R
import com.sequenia.filmsapp.databinding.FragmentFilmCardBinding
import com.sequenia.filmsapp.feature.toolbar.viewmodel.ToolbarViewModel

class FilmCardFragment : Fragment() {
    companion object {
        const val ARG_IMAGE = "ARG_IMAGE"
        const val ARG_NAME = "ARG_NAME"
        const val ARG_LOCALIZED_NAME = "ARG_LOCALIZED_NAME"
        const val ARG_INFORMATION = "ARG_INFORMATION"
        const val ARG_RATING = "ARG_RATING"
        const val ARG_DESCRIPTION = "ARG_DESCRIPTION"
    }

    private val toolbarViewModel by activityViewModels<ToolbarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentFilmCardBinding.inflate(inflater, container, false)

        val title = arguments?.getString(ARG_NAME)
        if (title != null) {
            toolbarViewModel.setTitle(title)
        }

        val imageUrl = arguments?.getString(ARG_IMAGE)

        if (imageUrl.isNullOrEmpty()) {
            Glide.with(binding.root)
                .load(R.drawable.empty_film_image)
                .into(binding.filmCardImage)
        } else {
            Glide.with(binding.root)
                .load(imageUrl)
                .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_radius)))
                .placeholder(R.drawable.empty_film_image)
                .error(R.drawable.empty_film_image)
                .into(binding.filmCardImage)
        }

        binding.filmCardName.text = arguments?.getString(ARG_LOCALIZED_NAME)

        binding.filmInformation.text = arguments?.getString(ARG_INFORMATION)

        val rating = arguments?.getDouble(ARG_RATING, -1.0)

        if (rating != null && rating >= 0.0) {
            binding.filmRating.text = arguments?.getDouble(ARG_RATING).toString()
        } else {
            binding.kinopoiskLabel.isVisible = false
        }

        binding.filmDescription.text = arguments?.getString(ARG_DESCRIPTION)

        return binding.root
    }
}