package com.example.minirobots.instructionsList.presentation.addinstructionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentAddInstructionBinding
import com.example.minirobots.instructionsList.presentation.instructionlist.InstructionsListViewModel
import com.example.minirobots.utilities.observeIn
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AddInstructionFragment : BottomSheetDialogFragment() {

    private val viewModel: AddInstructionViewModel by viewModels()
    private val instructionListViewModel: InstructionsListViewModel by activityViewModels()
    private val adapter = AddInstructionAdapter {
        viewModel.onInstructionAdded(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_instruction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
    }

    private fun setupBinding(view: View) {
        val binding = FragmentAddInstructionBinding.bind(view)
        binding.addInstructionsList.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.instructionsFlow.asLiveData()
            .observe(viewLifecycleOwner, { instructions ->
                adapter.submitList(instructions)
            })

        viewModel.eventsFlow
            .onEach {
                when (it) {
                    AddInstructionViewModel.Event.InstructionAdded -> onInstructionAdded()
                }
            }
            .observeIn(this)
    }

    private fun onInstructionAdded() {
        instructionListViewModel.onInstructionAdded()
        findNavController().navigateUp()
    }

}