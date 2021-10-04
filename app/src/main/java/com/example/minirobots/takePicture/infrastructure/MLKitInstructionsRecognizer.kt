package com.example.minirobots.takePicture.infrastructure

import android.net.Uri
import com.example.minirobots.instructionsList.domain.entities.Instruction
import com.example.minirobots.takePicture.domain.actions.GetInputImage
import com.example.minirobots.takePicture.domain.actions.RecognizeImage
import javax.inject.Inject

class MLKitInstructionsRecognizer @Inject constructor(
    private val getInputImage: GetInputImage,
    private val recognizeImage: RecognizeImage,
    private val mlKitTextMapper: MLKitTextMapper,
    private val getRecognizedInstructionNames: GetRecognizedInstructionNames
) {

    suspend fun recognize(uri: Uri): List<Instruction> {
        val inputImage = getInputImage(uri) ?: return emptyList()
        val mlKitText = recognizeImage(inputImage) ?: return emptyList()
        val recognizedInstructionNames = getRecognizedInstructionNames(mlKitText)

        return mlKitTextMapper.map(mlKitText)
    }

}
