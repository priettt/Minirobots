package com.example.minirobots.sendInstructions.domain.actions

import com.example.minirobots.common.domain.Action
import com.example.minirobots.common.domain.Instruction
import com.example.minirobots.sendInstructions.infrastructure.InstructionsParser
import com.example.minirobots.sendInstructions.infrastructure.InstructionsService
import com.example.minirobots.common.infrastructure.repository.FunctionsRepository
import com.example.minirobots.common.infrastructure.repository.InstructionsRepository
import javax.inject.Inject

class SendInstructions @Inject constructor(
    private val instructionsRepository: InstructionsRepository,
    private val functionsRepository: FunctionsRepository,
    private val instructionsParser: InstructionsParser,
    private val instructionsService: InstructionsService
) {
    suspend operator fun invoke(): SendInstructionsResult {
        val program = instructionsRepository.getAll()
        return if (isFunction(program)) {
            storeFunction(program)
        } else {
            sendProgram(program)
        }
    }

    private fun isFunction(program: List<Instruction>) = program.firstOrNull()?.action == Action.FUNCION_COMIENZO

    private fun storeFunction(program: List<Instruction>): SendInstructionsResult {
        functionsRepository.overwrite(program)
        return SendInstructionsResult.FunctionStored
    }

    private suspend fun sendProgram(program: List<Instruction>): SendInstructionsResult {
        val parsedInstructions = instructionsParser.parse(program)
        return try {
            val sendInstructionsResult = instructionsService.sendInstructions(parsedInstructions)
            if (sendInstructionsResult.isSuccessful) {
                SendInstructionsResult.ProgramSent
            } else {
                SendInstructionsResult.NetworkFailure
            }
        } catch (exception: Exception) {
            SendInstructionsResult.NetworkFailure
        }
    }
}

sealed class SendInstructionsResult {
    object ProgramSent : SendInstructionsResult()
    object FunctionStored : SendInstructionsResult()
    object NetworkFailure : SendInstructionsResult()
}
