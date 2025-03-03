package com.sequenia.filmsapp.feature.films.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sequenia.filmsapp.R
import com.sequenia.filmsapp.databinding.FragmentFilmsBinding
import com.sequenia.filmsapp.feature.filmcard.fragment.FilmCardFragment
import com.sequenia.filmsapp.feature.films.itemdecoration.OffsetDecoration
import com.sequenia.filmsapp.feature.films.adapter.FilmsAdapter
import com.sequenia.filmsapp.feature.films.api.FilmsApi
import com.sequenia.filmsapp.feature.films.data.Film
import com.sequenia.filmsapp.feature.films.repository.NetworkFilmsRepository
import com.sequenia.filmsapp.feature.films.viewmodel.FilmMapper
import com.sequenia.filmsapp.feature.films.viewmodel.FilmViewModel
import com.sequenia.filmsapp.feature.genres.adapter.GenresAdapter
import com.sequenia.filmsapp.feature.genres.data.Genre
import com.sequenia.filmsapp.util.getErrorText
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentFilmsBinding.inflate(inflater, container, false)

        var snackbar: Snackbar? = null

        val viewModel by viewModels<FilmViewModel> {
            viewModelFactory {
                initializer {
                    FilmViewModel(NetworkFilmsRepository(FilmsApi.INSTANCE), FilmMapper())
                }
            }
        }

        val filmsAdapter = FilmsAdapter(
            object : FilmsAdapter.FilmListener {
                override fun onFilmImageClicked(film: Film) {
                    navigateToFilmCard(film)
                }
            }
        )

        val genresAdapter = GenresAdapter(
            object : GenresAdapter.GenreListener {
                override fun onGenreClicked(genre: Genre) {
                    viewModel.setSelectedGenre(genre.id)
                }

                override fun onGenreDeselected() {
                    viewModel.setSelectedGenre(null)
                }
            }
        )

        binding.listOfGenres.isNestedScrollingEnabled = false
        binding.listOfFilms.isNestedScrollingEnabled = false

        binding.listOfGenres.layoutManager = LinearLayoutManager(requireContext())
        binding.listOfGenres.adapter = genresAdapter

        binding.listOfFilms.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.listOfFilms.addItemDecoration(
            OffsetDecoration(
                resources.getDimensionPixelSize(R.dimen.spacing_side),
                resources.getDimensionPixelSize(R.dimen.spacing_bottom),
                resources.getDimensionPixelSize(R.dimen.spacing_between_cards)
            )
        )
        binding.listOfFilms.adapter = filmsAdapter

        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (it.isError) {
                    val errorText =
                        it.status.throwableOrNull?.getErrorText(requireContext()).toString()

                    if (snackbar == null) {
                        snackbar = Snackbar.make(
                            binding.root,
                            errorText,
                            Snackbar.LENGTH_INDEFINITE
                        ).apply {
                            setAction(getString(R.string.snackbar_action_text)) {
                                viewModel.getFilms()
                            }
                            setActionTextColor(getColor(requireContext(), R.color.yellow))
                        }
                    } else {
                        snackbar?.setText(errorText)
                    }
                    snackbar?.show()
                } else {
                    snackbar?.dismiss()
                    snackbar = null
                }

                binding.progress.isVisible = it.isEmptyLoading
                binding.genresTitle.isVisible = !it.isEmptyLoading && !it.isError
                binding.filmsTitle.isVisible = !it.isEmptyLoading && !it.isError

                genresAdapter.submitList(it.uniqueGenresList)
                genresAdapter.updateSelectedGenreId(it.selectedGenreId)
                filmsAdapter.submitList(viewModel.getFilteredFilms())
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }

    private fun navigateToFilmCard(film: Film) {
        val filmInformation = createFilmInformationString(film)
        findNavController().navigate(
            R.id.action_filmsFragment_to_filmCardFragment,
            bundleOf(
                FilmCardFragment.ARG_IMAGE to film.imageUrl,
                FilmCardFragment.ARG_NAME to film.localizedName,
                FilmCardFragment.ARG_INFORMATION to filmInformation,
                FilmCardFragment.ARG_RATING to film.rating,
                FilmCardFragment.ARG_DESCRIPTION to film.description
            )
        )
    }

    private fun createFilmInformationString(film: Film): String {
        val genresString = if (film.genres.isNotEmpty()) {
            film.genres.joinToString(", ") { it.name } + ", "
        } else {
            ""
        }
        return "$genresString${film.year} ${getString(R.string.year_suffix)}"
    }
}