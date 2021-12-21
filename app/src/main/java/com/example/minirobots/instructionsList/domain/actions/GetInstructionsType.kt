package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.Action
import com.example.minirobots.Instruction
import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import javax.inject.Inject

class GetInstructionsType @Inject constructor(
    private val instructionsRepository: InstructionsRepository,
) {
    operator fun invoke(): InstructionsType? {
        val instructions = instructionsRepository.getAll()
        return when {
            isProgram(instructions) -> InstructionsType.Program
            isFunction(instructions) -> InstructionsType.Function
            else -> null
        }
    }

    private fun isProgram(instructions: List<Instruction>) =
        (instructions.getOrNull(0)?.action == Action.PROGRAMA_COMIENZO
                && instructions.lastOrNull()?.action == Action.PROGRAMA_FIN)

    private fun isFunction(instructions: List<Instruction>) =
        (instructions.getOrNull(0)?.action == Action.FUNCION_COMIENZO
                && instructions.lastOrNull()?.action == Action.FUNCION_FIN)

}

sealed class InstructionsType {
    object Program : InstructionsType()
    object Function : InstructionsType()
}