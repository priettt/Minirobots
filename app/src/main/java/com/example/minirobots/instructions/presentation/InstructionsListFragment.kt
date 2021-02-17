package com.example.minirobots.instructions.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentInstructionsListBinding
import com.example.minirobots.utilities.observeIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class InstructionsListFragment : Fragment(R.layout.fragment_instructions_list) {

    private val viewModel: InstructionsScreenViewModel by viewModels()
    private val adapter = InstructionsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
    }

    private fun setupBinding(view: View) {
        val binding = FragmentInstructionsListBinding.bind(view)
        binding.instructionsList.adapter = adapter
        binding.addButton.setOnClickListener {
            viewModel.onAddButtonClicked()
        }
    }

    private fun observeViewModel() {
        viewModel.instructionsFlow.asLiveData()
            .observe(viewLifecycleOwner, { instructions ->
                adapter.submitList(instructions)
            })

        viewModel.eventsFlow
            .onEach {
                when (it) {
                    Event.ShowAddInstructionPopUp -> showAddInstructionPopUp()
                    Event.ShowError -> showError()
                }
            }
            .observeIn(this)
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Couldn't retrieve instructions.", Toast.LENGTH_SHORT).show()
    }

    private fun showAddInstructionPopUp() {
        findNavController().navigate(
            InstructionsListFragmentDirections.actionInstructionsScreenFragmentToAddInstructionFragment()
        )
    }

}
