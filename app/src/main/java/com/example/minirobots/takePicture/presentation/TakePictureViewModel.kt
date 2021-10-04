package com.example.minirobots.takePicture.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.takePicture.domain.actions.RecognizeInstructions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TakePictureViewModel @Inject constructor(
    private val recognizeInstructions: RecognizeInstructions,
) : ViewModel() {

    private lateinit var pictureUri: Uri

    val navigation: MutableStateFlow<TakePictureNavigation?> = MutableStateFlow(null)

    fun onPictureUriCreated(pictureUri: Uri) {
        this.pictureUri = pictureUri
        navigation.value = TakePictureNavigation.ShowSpinner
    }

    fun onTakePictureCompleted(takePictureSuccessful: Boolean) {
        if (takePictureSuccessful) {
            recognizeInstructions()
        } else {
            navigation.value = TakePictureNavigation.ShowTakePictureError
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
    ShowTakePictureError,
    ShowSpinner
}