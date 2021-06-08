package com.example.minirobots.instructionsList.presentation.editinstructionmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructionsList.domain.actions.EditInstructionModifier
import com.example.minirobots.instructionsList.domain.actions.GetEditInstructionMenuData
import com.example.minirobots.instructionsList.domain.entities.Modifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditInstructionViewModel @Inject constructor(
    getEditInstructionMenuData: GetEditInstructionMenuData,
    private val editInstruction: EditInstructionModifier
) : ViewModel() {

    val modifiersFlow: MutableStateFlow<List<Modifier>?> = MutableStateFlow(null)
    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()
    private var instructionIndex = 0

    init {
        val editInstructionMenuData = getEditInstructionMenuData()
        modifiersFlow.value = editInstructionMenuData.availableModifiers
        instructionIndex = editInstructionMenuData.instructionIndex
    }

    fun onModifierClicked(modifier: Modifier) {
        editInstruction(instructionIndex, modifier)
        viewModelScope.launch {
            eventChannel.send(Event.InstructionEdited(instructionIndex))
        }
    }

    sealed class Event {
        data class InstructionEdited(val index: Int) : Event()
    }
}