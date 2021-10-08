package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.takePicture.domain.entities.InstructionName
import javax.inject.Inject

const val INSTRUCTION_DISTANCE_THRESHOLD = 3

/*
    Receives a text, compares it with all the available instruction names
    and returns the closest instruction name, if its distance is below the threshold.
 */

class GetClosestInstruction @Inject constructor(
    private val stringDistanceCalculator: StringDistanceCalculator,
    private val logger: MinirobotsLogger
) {

    operator fun invoke(text: String): InstructionName? {
        var closestInstruction = ClosestInstruction(null, Int.MAX_VALUE)

        for (instruction in InstructionName.values()) {
            val distanceWithText = stringDistanceCalculator.getDistance(text, instruction.text)
            if (distanceWithText < closestInstruction.distance) {
                closestInstruction = ClosestInstruction(instruction, distanceWithText)
            }
        }

        logClosestInstruction(text, closestInstruction)

        if (closestInstruction.distance <= INSTRUCTION_DISTANCE_THRESHOLD)
            return closestInstruction.name
        return null
    }

    private fun logClosestInstruction(text: String, closestInstruction: ClosestInstruction) {
        logger.log(
            this.javaClass.name,
            "Recognizing text: $text. Closest instruction ${closestInstruction.name?.text} with a distance ${closestInstruction.distance}"
        )
    }

    private data class ClosestInstruction(val name: InstructionName?, val distance: Int)
}