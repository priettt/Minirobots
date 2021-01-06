package com.example.minirobots.home.presentation

import android.content.Context
import android.net.Uri
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.home.domain.Instruction
import com.example.minirobots.home.domain.InstructionsService
import com.example.minirobots.utilities.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
    private val instructionsService: InstructionsService
) : ViewModel() {

    lateinit var pictureUri: Uri

    val state : MutableStateFlow<HomeUiState?> = MutableStateFlow(null)

    fun onTakePictureUriCreated(pictureUri: Uri) {
        this.pictureUri = pictureUri
    }

    fun onTakePictureCompleted() {
        viewModelScope.launch {
            when(val result = instructionsService.getInstructions(pictureUri, context)) {
                is Result.Success -> state.value = HomeUiState.ImageRecognitionSuccess(result.data)
                is Result.Error -> state.value = HomeUiState.ImageRecognitionError(result.exception)
            }
        }
    }

}

sealed class HomeUiState {
    data class ImageRecognitionSuccess(val instructions: List<Instruction>) : HomeUiState()
    data class ImageRecognitionError(val exception: Exception) : HomeUiState()
}