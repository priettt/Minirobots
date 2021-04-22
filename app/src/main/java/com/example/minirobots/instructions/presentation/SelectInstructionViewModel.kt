package com.example.minirobots.instructions.presentation

import androidx.lifecycle.ViewModel
import com.example.minirobots.instructions.domain.actions.AddInstructionItem
import com.example.minirobots.instructions.domain.actions.GetInstructionsForSelection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SelectInstructionViewModel @Inject constructor(
    getInstructionsForSelection: GetInstructionsForSelection
) : ViewModel() {

    val instructionsFlow: MutableStateFlow<List<AddInstructionItem>?> = MutableStateFlow(null)

    init {
        instructionsFlow.value = getInstructionsForSelection()
    }
}