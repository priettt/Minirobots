package com.example.minirobots.takePicture.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.takePicture.domain.actions.RecognizeInstructions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TakePictureViewModel @Inject constructor(
    private val recognizeInstructions: RecognizeInstructions,
) : ViewModel() {

    private var pictureUri: Uri? = null

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    fun onPictureUriCreated(pictureUri: Uri) {
        this.pictureUri = pictureUri
        sendEvent(Event.ShowSpinner)
    }

    fun onTakePictureCompleted(takePictureSuccessful: Boolean) {
        if (takePictureSuccessful) {
            recognizeInstructions()
        } else {
            sendEvent(Event.ShowTakePictureError)
        }
    }

    fun onGetPictureFromGallery(pictureUri: Uri?) {
        pictureUri?.let {
            onPictureUriCreated(it)
            recognizeInstructions()
        }
    }

    private fun recognizeInstructions() {
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            recognizeInstructions(pictureUri).onSuccess {
                Log.d("MINIROBOTS", "Instructions processed in ${System.currentTimeMillis() - startTime}")
                sendEvent(Event.GoToInstructions)
            }.onFailure {
                Log.d("MINIROBOTS", "Error ${it.localizedMessage}")
                sendEvent(Event.ShowRecognitionError)
            }
        }
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}

sealed class Event {
    object GoToInstructions : Event()
    object ShowRecognitionError : Event()
    object ShowTakePictureError : Event()
    object ShowSpinner : Event()
}