package com.example.minirobots.home.domain

import android.content.Context
import android.net.Uri
import com.example.minirobots.utilities.Result
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

interface InstructionsService {
    suspend fun getInstructions(uri: Uri, context: Context): Result<List<Instruction>>
}

class MlKitInstructionsService @Inject constructor(
    private val mlKitTextMapper: MLKitTextMapper,
    private val dispatcher: CoroutineDispatcher
) : InstructionsService {
    override suspend fun getInstructions(uri: Uri, context: Context): Result<List<Instruction>> =
        withContext(dispatcher) {
            try {
                val recognizer = TextRecognition.getClient()
                val inputImage = InputImage.fromFilePath(context, uri)
                val mlKitText = recognizer.process(inputImage).await()
                    ?: return@withContext Result.Error(Exception("MlKitRecognizerFailure"))
                return@withContext Result.Success(mlKitTextMapper.getInstructions(mlKitText))
            } catch (e: Exception) {
                return@withContext Result.Error(IOException())
            }
        }
}