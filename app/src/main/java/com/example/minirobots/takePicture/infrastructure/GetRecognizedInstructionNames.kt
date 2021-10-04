package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.takePicture.domain.entities.InstructionName
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class GetRecognizedInstructionNames @Inject constructor(
    private val getLocalizedInstruction: GetLocalizedInstruction
) {
    operator fun invoke(mlKitText: Text): List<InstructionName> {
        val list = mutableListOf<LocalizedInstruction>()
        for (block in mlKitText.textBlocks) {
            for (line in block.lines) {
                val localizedInstruction = getLocalizedInstruction(line) ?: continue
                list.add(localizedInstruction)
            }
        }
        return list
            .sortedBy { it.x }
            .map { it.type }
    }
}