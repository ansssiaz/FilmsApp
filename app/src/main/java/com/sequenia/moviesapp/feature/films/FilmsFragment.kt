package com.sequenia.moviesapp.feature.films

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
import com.google.android.material.snackbar.Snackbar
import com.sequenia.moviesapp.R
import com.sequenia.moviesapp.databinding.FragmentFilmsBinding
import com.sequenia.moviesapp.feature.films.adapter.FilmsAdapter
import com.sequenia.moviesapp.feature.films.api.FilmsApi
import com.sequenia.moviesapp.feature.films.data.Film
import com.sequenia.moviesapp.feature.films.repository.NetworkFilmsRepository
import com.sequenia.moviesapp.feature.films.viewmodel.FilmViewModel
import com.sequenia.moviesapp.util.getErrorText
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmsFragment : Fragment() {
    private var snackbar: Snackbar? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentFilmsBinding.inflate(inflater, container, false)
        val viewModel by viewModels<FilmViewModel> {
            viewModelFactory {
                initializer {
                    FilmViewModel(NetworkFilmsRepository(FilmsApi.INSTANCE))
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

        binding.listOfFilms.layoutManager = GridLayoutManager(requireContext(), 2)
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

                filmsAdapter.submitList(it.films)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}