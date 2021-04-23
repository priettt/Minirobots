package com.example.minirobots.instructions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructions.domain.actions.GetInstructions
import com.example.minirobots.instructions.domain.entities.Instruction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructionsListViewModel @Inject constructor(
    private val getInstructions: GetInstructions
) : ViewModel() {

    val instructionsFlow: MutableStateFlow<List<Instruction>?> = MutableStateFlow(null)

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        fetchInstructions()
    }

    fun onAddSheetDismissed() {
        fetchInstructions()
    }

    private fun fetchInstructions() {
        getInstructions().onSuccess { instructions ->
            instructionsFlow.value = instructions
        }.onFailure {
            sendEvent(Event.ShowError)
        }
    }

    fun onAddButtonClicked() {
        sendEvent(Event.ShowAddInstructionPopUp)
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

}

sealed class Event {
    object ShowAddInstructionPopUp : Event()
    object ShowError : Event()
}


