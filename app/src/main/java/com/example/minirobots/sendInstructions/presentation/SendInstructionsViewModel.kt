package com.example.minirobots.sendInstructions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.sendInstructions.domain.actions.SendInstructions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendInstructionsViewModel @Inject constructor(
    private val sendInstructions: SendInstructions
) : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    fun onViewCreated() {
        postInstructions()
    }

    private fun postInstructions() {
        viewModelScope.launch {
            sendInstructions()
                .onSuccess { sendEvent(Event.ShowSuccess) }
                .onFailure { sendEvent(Event.ShowFailure) }
        }
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    fun onRetryPressed() {
        sendEvent(Event.ShowLoading)
        postInstructions()
    }

    sealed class Event {
        object ShowSuccess : Event()
        object ShowFailure : Event()
        object ShowLoading : Event()
    }

}
