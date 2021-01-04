package com.example.minirobots.home.domain

import android.content.Context
import android.net.Uri
import android.util.Log
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
        val listOfInstructionsWithLocation = mutableListOf<InstructionWithLocation>()
        for (block in mlKitText.textBlocks) {
//            Log.d("MINIROBOTS", "Block ----------------------------------------------------------------------------------")
//            Log.d("MINIROBOTS", "Block text: ${block.text}")
//            Log.d("MINIROBOTS", "Block cornerPoints: ${block.cornerPoints?.get(0)?.x}")
//            Log.d("MINIROBOTS", "Block frame: ${block.boundingBox}")
            for (line in block.lines) {
//                Log.d("MINIROBOTS", "Line -----------------------------------------")
//                Log.d("MINIROBOTS", "Line text: ${line.text}")
//                Log.d("MINIROBOTS", "Line cornerPoints: ${line.cornerPoints?.get(0)?.x}")
////                Log.d("MINIROBOTS", "Line frame: ${line.boundingBox}")
                for (element in line.elements) {
//                    Log.d("MINIROBOTS", "Element -----------------------------------------")
//                    Log.d("MINIROBOTS", "Element text: ${element.text}")
//                    Log.d("MINIROBOTS", "Element cornerPoints: ${element.cornerPoints?.get(0)?.x}")
//                    Log.d("MINIROBOTS", "Element frame: ${element.boundingBox}")
                    listOfInstructionsWithLocation.add(
                        InstructionWithLocation(
                            element.text,
                            element.cornerPoints?.get(0)?.x ?: 0,
                            element.cornerPoints?.get(0)?.y ?: 0
                        )
                    )
                }
            }
        }
        listOfInstructionsWithLocation.sortBy { it.x }
        listOfInstructionsWithLocation.forEach {
            Log.d("MINIROBOTS", "Text: ${it.text} - X: ${it.x} - Y: ${it.y}")
        }
        listOfInstructionsWithLocation.sortBy { it.y }
        listOfInstructionsWithLocation.forEach {
            Log.d("MINIROBOTS", "Text: ${it.text} - X: ${it.x} - Y: ${it.y}")
        }
        return listOfInstructions
    }
}

data class InstructionWithLocation(
    val text: String,
    val x: Int, val y: Int
)
