package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.takePicture.domain.entities.InstructionName
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

/*
    Returns a list of all the instruction names recognized in the MLKitText
    The list is sorted from left to right. For now, y position is disregarded.
 */

class GetRecognizedInstructionNames @Inject constructor(
    private val getLocalizedInstruction: GetLocalizedInstruction
) {
    operator fun invoke(mlKitText: Text): List<InstructionName> {
        val localizedInstructions = mutableListOf<LocalizedInstruction>()
        for (block in mlKitText.textBlocks) {
            for (line in block.lines) {
                val localizedInstruction = getLocalizedInstruction(line) ?: continue
                localizedInstructions.add(localizedInstruction)
            }
        }
        return localizedInstructions
            .sortedBy { it.x }
            .map { it.type }
    }
}