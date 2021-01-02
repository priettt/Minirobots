package com.example.minirobots.home.domain

import android.content.Context
import android.net.Uri
import com.example.minirobots.utilities.Result
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

interface InstructionsRecognizer {
    suspend fun recognizeInstructions(uri: Uri, context: Context): Result<List<Instruction>>
}

class MlKitInstructionsRecognizer @Inject constructor(
    private val mlKitTextInstructionsExtractor: MlKitTextInstructionsExtractor,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : InstructionsRecognizer {
    override suspend fun recognizeInstructions(uri: Uri, context: Context): Result<List<Instruction>> =
        withContext(dispatcher) {
            val recognizer = TextRecognition.getClient()
            return@withContext try {
                val inputImage = InputImage.fromFilePath(context, uri)
                val recognizerResult = recognizer.process(inputImage).await()
                val instructionsList = mlKitTextInstructionsExtractor.gatherInstructions(recognizerResult)

                Result.Success(instructionsList)
            } catch (exception: IOException) {
                Result.Error(exception)
            }
        }
}

class MlKitTextInstructionsExtractor @Inject constructor() {
    fun gatherInstructions(mlKitText: Text): List<Instruction> {
        val listOfInstructions = mutableListOf<Instruction>()
        mlKitText.textBlocks.forEach { _ ->
            listOfInstructions.add(Instruction(InstructionType.BACKWARD))
        }
        return listOfInstructions
    }
}
