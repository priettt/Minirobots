package com.example.minirobots.instructionsList.presentation.addinstructionmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructionsList.domain.actions.AddInstruction
import com.example.minirobots.instructionsList.domain.actions.GetAvailableInstructions
import com.example.minirobots.instructionsList.domain.entities.UIInstruction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddInstructionViewModel @Inject constructor(
    getAvailableInstructions: GetAvailableInstructions,
    private val addInstruction: AddInstruction
) : ViewModel() {

    val instructionsFlow: MutableStateFlow<List<UIInstruction>?> = MutableStateFlow(null)

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        instructionsFlow.value = getAvailableInstructions()
    }

    fun onInstructionAdded(instruction: UIInstruction) {
        addInstruction(instruction)
        viewModelScope.launch {
            eventChannel.send(Event.InstructionAdded)
        }
    }

    sealed class Event {
        object InstructionAdded : Event()
    }
}