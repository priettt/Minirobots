package com.example.minirobots.sendInstructions.infrastructure

import com.example.minirobots.instructionsList.domain.entities.Instruction
import javax.inject.Inject

interface InstructionsParser {
    fun parse(instructions: List<Instruction>): String
}

class InstructionsParserImpl @Inject constructor() : InstructionsParser {
    override fun parse(instructions: List<Instruction>) =
        """[ ${getInstructionsBody(instructions)}]"""

    private fun getInstructionsBody(instructions: List<Instruction>) =
        instructions
            .filter { it.instructionBody.isNotEmpty() }
            .joinToString {
                it.instructionBody
            }

}