package com.example.minirobots.instructions.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructions.domain.actions.DeleteInstruction
import com.example.minirobots.instructions.domain.actions.GetInstructions
import com.example.minirobots.instructions.domain.actions.MoveInstruction
import com.example.minirobots.instructions.domain.entities.Instruction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructionsListViewModel @Inject constructor(
    private val getInstructions: GetInstructions,
    private val deleteInstruction: DeleteInstruction,
    private val moveInstruction: MoveInstruction,
) : ViewModel() {

    private val mutableInstructions = MutableLiveData<List<Instruction>>()
    val instructions: LiveData<List<Instruction>>
        get() = mutableInstructions

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
            mutableInstructions.value = instructions
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

    fun onItemDeleted(index: Int) {
        deleteInstruction(index)
        fetchInstructions()
    }

    fun onItemMoved(fromIndex: Int, toIndex: Int) {
        moveInstruction(fromIndex, toIndex)
        fetchInstructions()
    }

}

sealed class Event {
    object ShowAddInstructionPopUp : Event()
    object ShowError : Event()
}


