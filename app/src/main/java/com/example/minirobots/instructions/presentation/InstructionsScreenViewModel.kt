package com.example.minirobots.instructions.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.minirobots.instructions.domain.actions.GetInstructions
import kotlinx.coroutines.flow.MutableStateFlow

class InstructionsScreenViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    getInstructions: GetInstructions
) : ViewModel() {

    val instructionsFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    init {
        getInstructions().onSuccess { instructions ->
            instructionsFlow.value = instructions.joinToString { it.type.text }
        }.onFailure {
            instructionsFlow.value = "Couldn't retrieve instructions"
        }
    }
}



