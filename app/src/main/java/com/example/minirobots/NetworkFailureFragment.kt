package com.example.minirobots

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.minirobots.databinding.FragmentNetworkFailureBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NetworkFailureFragment : Fragment(R.layout.fragment_network_failure) {

    private val viewModel: NetworkFailureViewModel by viewModels()
    private lateinit var binding: FragmentNetworkFailureBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
    }

    private fun setupBinding(view: View) {
        binding = FragmentNetworkFailureBinding.bind(view)
        binding.sendInstructionsRetryButton.setOnClickListener {
            binding.loadingView.progressOverlay.visibility = View.VISIBLE
            viewModel.onRetryPressed()
        }
    }

    private fun observeViewModel() {
        viewModel.events
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(::handleEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleEvent(event: NetworkFailureViewModel.Event) {
        when (event) {
            NetworkFailureViewModel.Event.ShowError -> showError()
            NetworkFailureViewModel.Event.ShowSuccess -> showSuccess()
        }
    }

    private fun showSuccess() {
        binding.loadingView.progressOverlay.visibility = View.GONE
        findNavController().navigate(
            NetworkFailureFragmentDirections.actionNetworkFailureFragmentToInstructionsSentFragment()
        )
    }

    private fun showError() {
        binding.loadingView.progressOverlay.visibility = View.GONE
    }
}