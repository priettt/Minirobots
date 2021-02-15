package com.example.minirobots.home.presentation

import android.net.Uri
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.home.domain.actions.ProcessInstructions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TakePictureViewModel @ViewModelInject constructor(
    private val processInstructions: ProcessInstructions,
) : ViewModel() {

    private lateinit var pictureUri: Uri

    val navigation: MutableStateFlow<TakePictureNavigation?> = MutableStateFlow(null)

    fun onPictureUriCreated(pictureUri: Uri) {
        this.pictureUri = pictureUri
    }

    fun onTakePictureCompleted(takePictureSuccessful: Boolean) {
        if (takePictureSuccessful) {
            recognizeInstructions()
        } else {
            navigation.value = TakePictureNavigation.ShowTakePictureError
        }
    }

    fun onGetPictureFromGallery(pictureUri: Uri) {
        onPictureUriCreated(pictureUri)
        recognizeInstructions()
    }


    private fun recognizeInstructions() {
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            processInstructions(pictureUri).onSuccess {
                Log.d("AAAAAAAAAAAAA", "took ${System.currentTimeMillis() - startTime}")
                navigation.value = TakePictureNavigation.GoToInstructions
            }.onFailure {
                navigation.value = TakePictureNavigation.ShowRecognitionError
            }
        }
    }

}

enum class TakePictureNavigation {
    GoToInstructions,
    ShowRecognitionError,
    ShowTakePictureError
}