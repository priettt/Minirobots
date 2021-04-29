package com.example.minirobots.instructions.presentation.editinstructionmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructions.domain.actions.AddInstruction
import com.example.minirobots.instructions.domain.actions.GetModifiersList
import com.example.minirobots.instructions.domain.entities.Modifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditInstructionViewModel @Inject constructor(
    getModifiersList: GetModifiersList,
    private val addInstruction: AddInstruction
) : ViewModel() {

    val modifiersFlow: MutableStateFlow<List<Modifier>?> = MutableStateFlow(null)

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        modifiersFlow.value = getModifiersList()
    }

    fun onInstructionEdited(modifier: Modifier) {
        viewModelScope.launch {
            eventChannel.send(Event.InstructionEdited)
        }
    }

    sealed class Event {
        object InstructionEdited : Event()
    }
}