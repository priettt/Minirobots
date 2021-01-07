package com.example.minirobots.instructions.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.minirobots.home.domain.Instruction
import com.example.minirobots.home.presentation.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow

class InstructionsScreenViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val instructions: MutableStateFlow<String?> = MutableStateFlow(null)

    init {
        val instructionsList = savedStateHandle.get("instructions") as? Array<Instruction>
        instructionsList?.let {
            instructions.value = instructionsList.joinToString { it.type.text }
        }
    }
}



