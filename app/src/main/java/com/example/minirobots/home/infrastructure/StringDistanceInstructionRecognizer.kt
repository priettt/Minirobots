package com.example.minirobots.home.domain

import javax.inject.Inject

const val INSTRUCTION_DISTANCE_THRESHOLD = 3

class StringDistanceInstructionRecognizer @Inject constructor(
    private val stringDistanceCalculator: StringDistanceCalculator
) : InstructionRecognizer {
    override fun getInstructionType(text: String): InstructionType? {
        var closestDistance = Int.MAX_VALUE
        var closestInstruction = InstructionType.ANGULO_30
        for (instruction in InstructionType.values()) {
            if (stringDistanceCalculator.getDistance(text, instruction.text) < closestDistance) {
                closestDistance = stringDistanceCalculator.getDistance(text, instruction.text)
                closestInstruction = instruction
            }
        }
        if (closestDistance <= INSTRUCTION_DISTANCE_THRESHOLD)
            return closestInstruction
        return null
    }
}

interface InstructionRecognizer {
    fun getInstructionType(text: String): InstructionType?
}