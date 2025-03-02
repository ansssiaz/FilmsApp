package com.sequenia.filmsapp.feature.films.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sequenia.filmsapp.R
import com.sequenia.filmsapp.databinding.FragmentFilmsBinding
import com.sequenia.filmsapp.feature.films.OffsetDecoration
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
                    /*findNavController().navigate(
                        R.id.action_filmsFragment_to_FilmInformationFragment,
                        bundleOf(
                            FilmInformationFragment.ARG_FILM_ID to film.id,
                            ...
                        )
                    )*/
                }
            }
        )

        val genresAdapter = GenresAdapter(
            object : GenresAdapter.GenreListener {
                override fun onGenreClicked(genre: Genre) {
                    val filteredFilms =
                        viewModel.state.value.films?.filter { film -> genre.id in film.genres.map { it.id } }
                    filmsAdapter.submitList(filteredFilms)
                }

                override fun onGenreDeselected() {
                    filmsAdapter.submitList(viewModel.state.value.films)
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
                filmsAdapter.submitList(it.films)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }


}