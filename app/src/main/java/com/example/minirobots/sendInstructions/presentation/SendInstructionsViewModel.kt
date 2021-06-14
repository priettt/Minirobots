package com.example.minirobots.sendInstructions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minirobots.sendInstructions.domain.actions.SendInstructions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendInstructionsViewModel @Inject constructor(
    private val sendInstructions: SendInstructions
) : ViewModel() {
    fun onViewCreated() {
        viewModelScope.launch {
            sendInstructions()
        }
    }

}
