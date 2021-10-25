package com.example.minirobots.sendInstructions.presentation

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentSendInstructionsBinding
import com.example.minirobots.sendInstructions.presentation.SendInstructionsViewModel.Event
import com.example.minirobots.sendInstructions.presentation.SendInstructionsViewModel.Event.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SendInstructionsFragment : Fragment(R.layout.fragment_send_instructions) {

    private val viewModel: SendInstructionsViewModel by viewModels()
    private lateinit var binding: FragmentSendInstructionsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
        viewModel.onViewCreated()
    }

    private fun setupBinding(view: View) {
        binding = FragmentSendInstructionsBinding.bind(view)
        binding.sendInstructionsRetryButton.setOnClickListener {
            viewModel.onRetryPressed()
        }
    }

    private fun observeViewModel() {
        viewModel.events
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(::handleEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleEvent(event: Event) {
        when (event) {
            ShowFailure -> showFailure()
            ShowSuccess -> showSuccess()
            ShowLoading -> showLoading()
        }
    }

    private fun showSuccess() {
        binding.successGroup.visibility = VISIBLE
        binding.errorGroup.visibility = GONE
        binding.loadingGroup.visibility = GONE
    }

    private fun showLoading() {
        binding.loadingGroup.visibility = VISIBLE
        binding.successGroup.visibility = GONE
        binding.errorGroup.visibility = GONE
    }

    private fun showFailure() {
        binding.loadingGroup.visibility = GONE
        binding.successGroup.visibility = GONE
        binding.errorGroup.visibility = VISIBLE
    }

}