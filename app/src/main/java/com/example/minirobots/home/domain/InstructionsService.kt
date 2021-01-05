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
    private val MLKitTextMapper: MLKitTextMapper,
    private val dispatcher: CoroutineDispatcher
) : InstructionsService {
    override suspend fun getInstructions(uri: Uri, context: Context): Result<List<Instruction>> =
        withContext(dispatcher) {
            return@withContext try {
                //Process with MLKit
                val recognizer = TextRecognition.getClient()
                val inputImage = InputImage.fromFilePath(context, uri)
                val recognizerResult = recognizer.process(inputImage).await()
                recognizer.close()

                //Translate MLKit Text to instructions
                val instructionsList = MLKitTextMapper.getInstructions(recognizerResult)
                Result.Success(instructionsList)
            } catch (exception: IOException) {
                Result.Error(exception)
            }
        }
}