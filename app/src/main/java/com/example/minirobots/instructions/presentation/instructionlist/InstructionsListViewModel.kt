package com.example.minirobots.instructions.presentation.instructionlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructions.domain.actions.*
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
    private val getInstructionModifiers: GetInstructionModifiers,
    private val storeEditInstructionMenuData: StoreEditInstructionMenuData,
) : ViewModel() {

    private val mutableInstructions = MutableLiveData<List<Instruction>>()
    val instructions: LiveData<List<Instruction>>
        get() = mutableInstructions

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        fetchInstructions()
    }

    fun onInstructionAdded() {
        fetchInstructions()
    }

    fun onInstructionEdited(index: Int) {
        sendEvent(Event.ForceUpdate(index))
    }

    private fun fetchInstructions() {
        getInstructions().onSuccess { instructions ->
            mutableInstructions.value = instructions
        }.onFailure {
            sendEvent(Event.ShowError)
        }
    }

    fun onAddButtonClicked() {
        sendEvent(Event.ShowAddInstructionMenu)
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

    fun onInstructionClicked(index: Int) {
        getInstructionModifiers(index)?.let {
            storeEditInstructionMenuData(index, it)
            sendEvent(Event.ShowEditInstructionMenu)
        }
    }

}

sealed class Event {
    object ShowAddInstructionMenu : Event()
    object ShowEditInstructionMenu : Event()
    object ShowError : Event()
    data class ForceUpdate(val index: Int) : Event()
}


