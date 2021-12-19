package com.example.minirobots.sendInstructions.infrastructure

import com.example.minirobots.Action
import com.example.minirobots.Instruction
import com.example.minirobots.takePicture.infrastructure.FunctionsRepository
import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import javax.inject.Inject

class ProgramValidator @Inject constructor(
    private val instructionsRepository: InstructionsRepository,
    private val functionsRepository: FunctionsRepository
) {
    fun getProgramValidationState(): ProgramValidationState {
        val program = instructionsRepository.getAll()
        return when {
            program.isEmpty() -> ProgramValidationState.EmptyProgram
            !hasCorrectStartAndEndInstructions(program) -> ProgramValidationState.IncorrectStartAndEndInstructions
            functionNotStored(program) -> ProgramValidationState.FunctionNotStored
            else -> ProgramValidationState.Valid
        }
    }

    private fun functionNotStored(program: List<Instruction>): Boolean {
        return isFunctionPresentInProgram(program) && functionIsNotStored()
    }

    private fun isFunctionPresentInProgram(program: List<Instruction>) = program.any {
        it.action == Action.FUNCION
    }

    private fun functionIsNotStored() = functionsRepository.getAll().isEmpty()

    private fun hasCorrectStartAndEndInstructions(program: List<Instruction>) =
        startsAndEndsWithProgramInstructions(program) || startsAndEndsWithFunctionInstructions(program)

    private fun startsAndEndsWithProgramInstructions(program: List<Instruction>): Boolean {
        return program.getOrNull(0)?.action == Action.PROGRAMA_COMIENZO
                && program.lastOrNull()?.action == Action.PROGRAMA_FIN
    }

    private fun startsAndEndsWithFunctionInstructions(program: List<Instruction>): Boolean {
        return program.getOrNull(0)?.action == Action.FUNCION_COMIENZO
                && program.lastOrNull()?.action == Action.FUNCION_FIN
    }
}

sealed class ProgramValidationState {
    object Valid : ProgramValidationState()
    object IncorrectStartAndEndInstructions : ProgramValidationState()
    object FunctionNotStored : ProgramValidationState()
    object EmptyProgram : ProgramValidationState()
}