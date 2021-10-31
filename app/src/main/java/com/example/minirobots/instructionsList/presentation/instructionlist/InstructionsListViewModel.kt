package com.example.minirobots.instructionsList.presentation.instructionlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructionsList.domain.actions.DeleteInstruction
import com.example.minirobots.instructionsList.domain.actions.GetUIInstructions
import com.example.minirobots.instructionsList.domain.actions.MoveInstruction
import com.example.minirobots.instructionsList.domain.entities.UIInstruction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructionsListViewModel @Inject constructor(
    private val getUIInstructions: GetUIInstructions,
    private val deleteInstruction: DeleteInstruction,
    private val moveInstruction: MoveInstruction,
) : ViewModel() {

    var clickedInstruction = 0

    private val mutableInstructions = MutableStateFlow<List<UIInstruction>>(emptyList())
    val instructions: StateFlow<List<UIInstruction>>
        get() = mutableInstructions

    private val mutableEvents = Channel<Event>(Channel.BUFFERED)
    val events = mutableEvents.receiveAsFlow()

    fun onViewCreated() {
        fetchInstructions()
    }

    fun onInstructionAdded() {
        fetchInstructions()
        sendEvent(Event.ScrollToBottom)
    }

    fun onInstructionEdited() {
        fetchInstructions()
    }

    fun onItemDeleted(index: Int) {
        deleteInstruction(index)
        fetchInstructions()
    }

    fun onItemMoved(fromIndex: Int, toIndex: Int) {
        moveInstruction(fromIndex, toIndex)
        fetchInstructions()
    }

    fun onInstructionClicked(index: Int) {
        clickedInstruction = index
        sendEvent(Event.ShowEditInstructionMenu)
    }

    fun onAddButtonClicked() {
        sendEvent(Event.ShowAddInstructionMenu)
    }

    fun onSendInstructionsButtonClicked() {
        sendEvent(Event.ShowSendInstructionsScreen)
    }

    private fun fetchInstructions() {
        mutableInstructions.value = getUIInstructions()
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            mutableEvents.send(event)
        }
    }
}

sealed class Event {
    object ShowAddInstructionMenu : Event()
    object ShowEditInstructionMenu : Event()
    object ShowSendInstructionsScreen : Event()
    object ScrollToBottom : Event()
}


