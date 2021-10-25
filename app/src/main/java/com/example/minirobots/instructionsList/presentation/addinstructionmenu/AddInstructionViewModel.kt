package com.example.minirobots.instructionsList.presentation.addinstructionmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructionsList.domain.actions.AddInstruction
import com.example.minirobots.instructionsList.domain.actions.GetAvailableActions
import com.example.minirobots.instructionsList.domain.entities.UIAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddInstructionViewModel @Inject constructor(
    getAvailableActions: GetAvailableActions,
    private val addInstruction: AddInstruction
) : ViewModel() {

    private val mutableAvailableActions: MutableStateFlow<List<UIAction>> = MutableStateFlow(emptyList())
    val availableInstructions: StateFlow<List<UIAction>> = mutableAvailableActions

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    init {
        mutableAvailableActions.value = getAvailableActions()
    }

    fun onInstructionAdded(instruction: UIAction) {
        addInstruction(instruction)
        viewModelScope.launch {
            eventChannel.send(Event.InstructionAdded)
        }
    }

    sealed class Event {
        object InstructionAdded : Event()
    }
}