package com.example.minirobots.instructions.presentation

import androidx.lifecycle.ViewModel
import com.example.minirobots.instructions.domain.actions.GetAvailableInstructions
import com.example.minirobots.instructions.domain.entities.Instruction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SelectInstructionViewModel @Inject constructor(
    getAvailableInstructions: GetAvailableInstructions
) : ViewModel() {

    val instructionsFlow: MutableStateFlow<List<Instruction>?> = MutableStateFlow(null)

    init {
        instructionsFlow.value = getAvailableInstructions()
    }
}
