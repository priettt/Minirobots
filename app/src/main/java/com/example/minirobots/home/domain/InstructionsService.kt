package com.example.minirobots.home.domain

import android.content.Context
import android.net.Uri
import com.example.minirobots.di.DefaultDispatcher
import com.example.minirobots.di.IoDispatcher
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
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
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : InstructionsService {

    private val recognizer: TextRecognizer by lazy {
        TextRecognition.getClient()
    }

    override suspend fun getInstructions(uri: Uri, context: Context): Result<List<Instruction>> {
        val inputImage = getInputImage(uri, context) ?: return Result.failure(IOException())
        val mlKitText = processImage(inputImage)
        val instructions = mapMLKitText(mlKitText)
        return if (instructions.isEmpty()) {
            Result.failure(Exception("Empty instructions result"))
        } else {
            Result.success(instructions)
        }

    }

    private suspend fun getInputImage(uri: Uri, context: Context) = withContext(ioDispatcher) {
        return@withContext try {
            InputImage.fromFilePath(context, uri)
        } catch (e: IOException) {
            null
        }
    }

    private suspend fun processImage(inputImage: InputImage) = withContext(defaultDispatcher) {
        return@withContext recognizer.process(inputImage).await()
    }

    private suspend fun mapMLKitText(mlKitText: Text) = withContext(defaultDispatcher) {
        return@withContext mlKitTextMapper.getInstructions(mlKitText)
    }
}