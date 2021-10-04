package com.example.minirobots.takePicture.infrastructure

import android.util.Log
import com.example.minirobots.takePicture.domain.entities.InstructionName
import javax.inject.Inject

const val INSTRUCTION_DISTANCE_THRESHOLD = 3

class GetClosestInstruction @Inject constructor(
    private val stringDistanceCalculator: StringDistanceCalculator
) {
    operator fun invoke(text: String): InstructionName? {
        var closestDistance = Int.MAX_VALUE
        var closestInstruction: InstructionName? = null

        for (instruction in InstructionName.values()) {
            val distanceWithText = stringDistanceCalculator.getDistance(text, instruction.text)
            if (distanceWithText < closestDistance) {
                closestDistance = distanceWithText
                closestInstruction = instruction
            }
        }

        Log.d(
            "Instruction Recognizer",
            "Recognizing text: $line. Closest instruction ${closestInstruction.text} with a distance $closestDistance"
        )

        if (closestDistance <= INSTRUCTION_DISTANCE_THRESHOLD)
            return closestInstruction

        return null
    }
}