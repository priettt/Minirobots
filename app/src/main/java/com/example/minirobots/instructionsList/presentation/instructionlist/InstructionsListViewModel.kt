package com.example.minirobots.instructionsList.presentation.instructionlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.instructionsList.domain.actions.*
import com.example.minirobots.instructionsList.domain.entities.UIInstruction
import com.example.minirobots.sendInstructions.domain.actions.SendInstructions
import com.example.minirobots.sendInstructions.domain.actions.SendInstructionsResult
import com.example.minirobots.sendInstructions.infrastructure.ProgramValidationState
import com.example.minirobots.sendInstructions.infrastructure.ProgramValidator
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
    private val programValidator: ProgramValidator,
    private val sendInstructions: SendInstructions,
    private val getInstructionsType: GetInstructionsType
) : ViewModel() {

    var clickedInstruction = 0

    private val mutableInstructions = MutableStateFlow<List<UIInstruction>>(emptyList())
    val instructions: StateFlow<List<UIInstruction>>
        get() = mutableInstructions

    private val mutableErrorState = MutableStateFlow<InstructionsListError>(InstructionsListError.NoError)
    val errorState: StateFlow<InstructionsListError>
        get() = mutableErrorState

    private val mutableInstructionsType = MutableStateFlow<InstructionsType>(InstructionsType.Program)
    val instructionsType: StateFlow<InstructionsType>
        get() = mutableInstructionsType

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
        sendEvent(Event.ShowLoadingScreen)
        viewModelScope.launch {
            when (sendInstructions()) {
                SendInstructionsResult.FunctionStored -> sendEvent(Event.ShowFunctionStoredScreen)
                SendInstructionsResult.NetworkFailure -> sendEvent(Event.ShowNetworkFailureScreen)
                SendInstructionsResult.ProgramSent -> sendEvent(Event.ShowProgramSentScreen)
            }
        }
    }

    private fun fetchInstructions() {
        mutableInstructions.value = getUIInstructions()
        mutableErrorState.value = getErrorState()
        mutableInstructionsType.value = getInstructionsType() ?: InstructionsType.Program
    }

    private fun getErrorState(): InstructionsListError =
        when (programValidator.getProgramValidationState()) {
            ProgramValidationState.FunctionNotStored -> InstructionsListError.FunctionNotStoredError
            ProgramValidationState.IncorrectStartAndEndInstructions -> InstructionsListError.InvalidProgramError
            ProgramValidationState.EmptyProgram -> InstructionsListError.EmptyProgramError
            ProgramValidationState.Valid -> InstructionsListError.NoError
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
    object ShowNetworkFailureScreen : Event()
    object ShowProgramSentScreen : Event()
    object ShowFunctionStoredScreen : Event()
    object ShowLoadingScreen : Event()
    object ScrollToBottom : Event()
}

sealed class InstructionsListError {
    object NoError : InstructionsListError()
    object InvalidProgramError : InstructionsListError()
    object EmptyProgramError : InstructionsListError()
    object FunctionNotStoredError : InstructionsListError()
}



