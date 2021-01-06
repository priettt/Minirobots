package com.example.minirobots.home.domain

import android.util.Log
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class MLKitTextMapper @Inject constructor(
    private val instructionTypeRecognizer: InstructionRecognizer
) {
    fun getInstructions(mlKitText: Text): List<Instruction> {
        Log.i("MINIROBOTS", "MAPPING ${System.nanoTime()}")
        Log.i("MINIROBOTS", "MLKIT TEXT BLOCKS SIZE = ${mlKitText.textBlocks.size}")
        val listOfInstructionsWithLocation = mutableListOf<InstructionWithLocation>()
        for (block in mlKitText.textBlocks) {
            for (line in block.lines) {
                val instructionType = instructionTypeRecognizer.getInstructionType(line.text)
                instructionType?.let {
                    listOfInstructionsWithLocation.add(
                        InstructionWithLocation(
                            it,
                            line.cornerPoints?.get(0)?.x ?: 0,
                            line.cornerPoints?.get(0)?.y ?: 0
                        )
                    )
                }
            }
        }

        return listOfInstructionsWithLocation
            .sortedBy { it.x }
            .map { Instruction(it.type) }
    }
}

data class InstructionWithLocation(
    val type: InstructionType,
    val x: Int, val y: Int
)


