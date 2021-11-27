package com.example.minirobots.sendInstructions.domain.actions

import com.example.minirobots.sendInstructions.infrastructure.InstructionsParser
import com.example.minirobots.sendInstructions.infrastructure.InstructionsService
import com.example.minirobots.sendInstructions.infrastructure.ProgramValidator
import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import javax.inject.Inject

class SendInstructions @Inject constructor(
    private val instructionsRepository: InstructionsRepository,
    private val programValidator: ProgramValidator,
    private val instructionsParser: InstructionsParser,
    private val instructionsService: InstructionsService
) {
    suspend operator fun invoke(): Result<Unit> {
        val instructions = instructionsRepository.getAll()
        if (!programValidator.isProgramValid(instructions)) {
            return Result.failure(SendInstructionsError.InvalidProgram)
        }
        val parsedInstructions = instructionsParser.parse(instructions)
        return try {
            val sendInstructionsResult = instructionsService.sendInstructions(parsedInstructions)
            if (sendInstructionsResult.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(SendInstructionsError.NetworkFailure)
            }
        } catch (exception: Exception) {
            Result.failure(SendInstructionsError.NetworkFailure)
        }
    }
}

sealed class SendInstructionsError : Error() {
    object InvalidProgram : SendInstructionsError()
    object NetworkFailure : SendInstructionsError()
}
