package com.example.minirobots.home.domain

import javax.inject.Inject

class StringDistanceInstructionRecognizer @Inject constructor(private val stringDistanceCalculator: StringDistanceCalculator): InstructionRecognizer {
    override fun getInstructionType(text: String): InstructionType? {
        var closestDistance = Int.MAX_VALUE
        var closestInstruction = InstructionType.ANGULO_30
        for (instruction in InstructionType.values()) {
            if (stringDistanceCalculator.getDistance(text, instruction.text) < closestDistance) {
                closestDistance = stringDistanceCalculator.getDistance(text, instruction.text)
                closestInstruction = instruction
            }
        }
        if (closestDistance <= 3)
            return closestInstruction
        return null
    }
}

interface InstructionRecognizer {
    fun getInstructionType(text: String): InstructionType?
}