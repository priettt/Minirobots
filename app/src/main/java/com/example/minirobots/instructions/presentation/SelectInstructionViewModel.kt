package com.example.minirobots.instructions.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.minirobots.instructions.domain.actions.GetInstructionsForSelection
import com.example.minirobots.instructions.domain.actions.AddInstructionItem
import kotlinx.coroutines.flow.MutableStateFlow

class SelectInstructionViewModel @ViewModelInject constructor(
    getInstructionsForSelection: GetInstructionsForSelection
) : ViewModel() {

    val instructionsFlow: MutableStateFlow<List<AddInstructionItem>?> = MutableStateFlow(null)

    init {
        instructionsFlow.value = getInstructionsForSelection()
    }
}