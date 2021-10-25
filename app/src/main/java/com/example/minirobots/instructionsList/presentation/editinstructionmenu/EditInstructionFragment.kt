package com.example.minirobots.instructionsList.presentation.editinstructionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentEditInstructionBinding
import com.example.minirobots.instructionsList.presentation.instructionlist.InstructionsListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditInstructionFragment : BottomSheetDialogFragment() {

    private val viewModel: EditInstructionViewModel by viewModels()
    private val instructionListViewModel: InstructionsListViewModel by activityViewModels()
    private val adapter = EditInstructionAdapter { modifier ->
        viewModel.onModifierClicked(modifier)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_instruction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
        viewModel.onViewCreated(instructionListViewModel.clickedInstruction)
    }

    private fun setupBinding(view: View) {
        val binding = FragmentEditInstructionBinding.bind(view)
        binding.editInstructionList.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.modifiers.collect {
                    adapter.submitList(it)
                }
            }
        }

        viewModel.events
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(::handleEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onInstructionEdited() {
        instructionListViewModel.onInstructionEdited()
        findNavController().navigateUp()
    }

    private fun handleEvent(event: EditInstructionViewModel.Event) {
        when (event) {
            EditInstructionViewModel.Event.InstructionEdited -> onInstructionEdited()
        }
    }

}