package com.example.minirobots.instructionsList.presentation.editinstructionmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructionsList.domain.actions.EditInstructionModifier
import com.example.minirobots.instructionsList.domain.actions.GetInstructionModifiers
import com.example.minirobots.instructionsList.domain.entities.UIModifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditInstructionViewModel @Inject constructor(
    private val getInstructionModifiers: GetInstructionModifiers,
    private val editInstruction: EditInstructionModifier
) : ViewModel() {

    private val mutableModifiers: MutableStateFlow<List<UIModifier>> = MutableStateFlow(emptyList())
    val modifiers: StateFlow<List<UIModifier>>
        get() = mutableModifiers

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    private var clickedInstruction = 0

    fun onModifierClicked(uiModifier: UIModifier) {
        editInstruction(clickedInstruction, uiModifier)
        viewModelScope.launch {
            eventChannel.send(Event.InstructionEdited)
        }
    }

    fun onViewCreated(clickedInstruction: Int) {
        this.clickedInstruction = clickedInstruction
        mutableModifiers.value = getInstructionModifiers(clickedInstruction)
    }

    sealed class Event {
        object InstructionEdited : Event()
    }
}