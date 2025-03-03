package com.sequenia.filmsapp.feature.toolbar.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sequenia.filmsapp.R
import com.sequenia.filmsapp.databinding.FragmentToolbarBinding
import com.sequenia.filmsapp.feature.toolbar.viewmodel.ToolbarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ToolbarFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragmentManager.beginTransaction()
            .setPrimaryNavigationFragment(this)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentToolbarBinding.inflate(inflater, container, false)

        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()

        navController.addOnDestinationChangedListener { _, _, _ ->
            if (navController.previousBackStackEntry == null) {
                binding.toolbar.navigationIcon = null
            } else {
                binding.toolbar.navigationIcon =
                    getDrawable(requireContext(), R.drawable.arrow_left_25)
                binding.toolbar.setNavigationOnClickListener {
                    navController.navigateUp()
                }
            }
        }

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        toolbarViewModel.title.onEach { title ->
            binding.toolbarTitle.text = title
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}