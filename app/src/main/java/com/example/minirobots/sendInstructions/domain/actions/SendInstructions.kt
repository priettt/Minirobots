package com.example.minirobots.sendInstructions.domain.actions

import com.example.minirobots.sendInstructions.infrastructure.InstructionsParser
import com.example.minirobots.sendInstructions.infrastructure.InstructionsService
import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import javax.inject.Inject

class SendInstructions @Inject constructor(
    private val instructionsRepository: InstructionsRepository,
    private val instructionsParser: InstructionsParser,
    private val instructionsService: InstructionsService,
) {
    suspend operator fun invoke(): Result<String> {
        val instructions = instructionsRepository.getAll()
        val parsedInstructions = instructionsParser.parse(instructions)
        return try {
            val sendInstructionsResult = instructionsService.sendInstructions(parsedInstructions)
            if (sendInstructionsResult.isSuccessful) {
                Result.success("")
            } else {
                Result.failure(Error("Network error"))
            }
        } catch (exception: Exception) {
            Result.failure(Error("Network error"))
        }
    }

}
