package com.example.minirobots.takePicture.domain.actions

import android.net.Uri
import com.example.minirobots.di.DefaultDispatcher
import com.example.minirobots.common.infrastructure.repository.InstructionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
    Call instructions recognizer and if we get recognized instructions, store them in the instructions repository.
 */

class RecognizeInstructions @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val instructionsRepository: InstructionsRepository,
    private val getInstructionsFromUri: GetInstructionsFromUri
) {

    suspend operator fun invoke(uri: Uri?): Result<Unit> = withContext(dispatcher) {
        if (uri == null) return@withContext Result.failure(Error("Null picture uri"))

        val recognizedInstructions = getInstructionsFromUri(uri)
        if (recognizedInstructions.isEmpty())
            Result.failure(Error("Recognition Error"))
        else {
            instructionsRepository.overwrite(recognizedInstructions)
            Result.success(Unit)
        }
    }

}