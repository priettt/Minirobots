package com.example.minirobots.home.presentation

import android.content.Context
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.home.domain.Instruction
import com.example.minirobots.home.domain.InstructionsService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val instructionsService: InstructionsService,
) : ViewModel() {

    lateinit var pictureUri: Uri

    val state: MutableStateFlow<HomeUiState?> = MutableStateFlow(null)

    fun onTakePictureUriCreated(pictureUri: Uri) {
        this.pictureUri = pictureUri
    }

    fun onTakePictureCompleted() {
        viewModelScope.launch {
            instructionsService.getInstructions(pictureUri, context).onSuccess {
                state.value = HomeUiState.InstructionsRecognitionSuccess(it)
            }.onFailure {
                state.value = HomeUiState.InstructionsRecognitionFailure(it)
            }
        }
    }

}

sealed class HomeUiState {
    data class InstructionsRecognitionSuccess(val instructions: List<Instruction>) : HomeUiState()
    data class InstructionsRecognitionFailure(val throwable: Throwable) : HomeUiState()
}