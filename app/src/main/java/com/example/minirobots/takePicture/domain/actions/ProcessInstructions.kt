package com.example.minirobots.takePicture.domain.actions

import android.content.Context
import android.net.Uri
import com.example.minirobots.di.DefaultDispatcher
import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import com.example.minirobots.takePicture.infrastructure.MLKitTextMapper
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ProcessInstructions @Inject constructor(
    private val mlKitTextMapper: MLKitTextMapper,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val instructionsRepository: InstructionsRepository
) {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend operator fun invoke(uri: Uri): Result<Unit> {
        val inputImage = getInputImage(uri, context) ?: return Result.failure(IOException())
        val mlKitText = processImage(inputImage)
        val instructions = mapMLKitText(mlKitText)
        return if (instructions.isEmpty()) {
            Result.failure(Exception("Empty instructions result"))
        } else {
            instructionsRepository.overwrite(instructions)
            Result.success(Unit)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun getInputImage(uri: Uri, context: Context) = withContext(dispatcher) {
        return@withContext try {
            InputImage.fromFilePath(context, uri)
        } catch (e: IOException) {
            null
        }
    }

    private suspend fun processImage(inputImage: InputImage) = withContext(dispatcher) {
        return@withContext recognizer.process(inputImage).await()
    }

    private suspend fun mapMLKitText(mlKitText: Text) = withContext(dispatcher) {
        return@withContext mlKitTextMapper.getInstructions(mlKitText)
    }
}