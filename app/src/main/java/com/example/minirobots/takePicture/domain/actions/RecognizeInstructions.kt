package com.example.minirobots.takePicture.domain.actions

import android.net.Uri
import com.example.minirobots.di.DefaultDispatcher
import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import com.example.minirobots.takePicture.infrastructure.MLKitInstructionsRecognizer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecognizeInstructions @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val instructionsRepository: InstructionsRepository,
    private val mlKitInstructionsRecognizer: MLKitInstructionsRecognizer
) {

    suspend operator fun invoke(uri: Uri): Result<Unit> = withContext(dispatcher) {
        val recognizedInstructions = mlKitInstructionsRecognizer.recognize(uri)
        if (recognizedInstructions.isEmpty())
            Result.failure(Error("Recognition Error"))
        else {
            instructionsRepository.overwrite2(recognizedInstructions)
            Result.success(Unit)
        }
    }

}