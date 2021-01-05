package com.example.minirobots.home.domain

import android.util.Log
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class MLKitTextMapper @Inject constructor(
    private val instructionTypeRecognizer: InstructionRecognizer
) {
    fun getInstructions(mlKitText: Text): List<Instruction> {
        val listOfInstructions = mutableListOf<Instruction>()
        val listOfInstructionsWithLocation = mutableListOf<InstructionWithLocation>()
        for (block in mlKitText.textBlocks) {
            for (line in block.lines) {
                val instructionType = instructionTypeRecognizer.getInstructionType(line.text)
                instructionType?.let {
                    listOfInstructions.add(Instruction(it))
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
        listOfInstructionsWithLocation.sortBy { it.x }
        listOfInstructionsWithLocation.forEach {
            Log.i("MINIROBOTS", "Text: ${it.text} - X: ${it.x} - Y: ${it.y}")
        }
        Log.i("MINIROBOTS", "SORTING Y ---------------------------")
        listOfInstructionsWithLocation.sortBy { it.y }
        listOfInstructionsWithLocation.forEach {
            Log.i("MINIROBOTS", "Text: ${it.text} - X: ${it.x} - Y: ${it.y}")
        }
        Log.d("MINIROBOTS", "Leaving Gatherer")
        return listOfInstructions
    }
}

data class InstructionWithLocation(
    val text: InstructionType,
    val x: Int, val y: Int
)


