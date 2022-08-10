package com.example.minirobots.networkFailure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.sendInstructions.domain.actions.SendInstructions
import com.example.minirobots.sendInstructions.domain.actions.SendInstructionsResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkFailureViewModel @Inject constructor(
    private val sendInstructions: SendInstructions
) : ViewModel() {

    private val mutableEvents = Channel<Event>(Channel.BUFFERED)
    val events = mutableEvents.receiveAsFlow()

    fun onRetryPressed() {
        viewModelScope.launch {
            when (sendInstructions()) {
                SendInstructionsResult.NetworkFailure -> sendEvent(Event.ShowError)
                SendInstructionsResult.ProgramSent -> sendEvent(Event.ShowSuccess)
                SendInstructionsResult.FunctionStored -> sendEvent(Event.ShowSuccess) //should not happen
            }
        }
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            mutableEvents.send(event)
        }
    }

    sealed class Event {
        object ShowError : Event()
        object ShowSuccess : Event()
    }
}