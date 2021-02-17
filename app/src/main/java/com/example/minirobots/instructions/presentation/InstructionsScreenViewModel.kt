package com.example.minirobots.instructions.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructions.domain.actions.GetInstructions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class InstructionsScreenViewModel @ViewModelInject constructor(
    getInstructions: GetInstructions
) : ViewModel() {

    val instructionsFlow: MutableStateFlow<List<InstructionItem>?> = MutableStateFlow(null)

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun onAddButtonClicked() {
        sendEvent(Event.ShowAddInstructionPopUp)
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    init {
        getInstructions().onSuccess { instructions ->
            instructionsFlow.value = instructions.map { InstructionItem(it.type.text) }
        }.onFailure {
            sendEvent(Event.ShowError)
        }
    }

}

sealed class Event {
    object ShowAddInstructionPopUp : Event()
    object ShowError : Event()
}


