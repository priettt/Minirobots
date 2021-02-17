package com.example.minirobots.instructions.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentAddInstructionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddInstructionFragment : BottomSheetDialogFragment() {

    private val viewModel: SelectInstructionViewModel by viewModels()
    private val adapter = AddInstructionAdapter()

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
    }

}