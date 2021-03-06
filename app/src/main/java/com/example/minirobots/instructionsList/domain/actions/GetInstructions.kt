package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import com.example.minirobots.instructionsList.domain.entities.Instruction
import javax.inject.Inject

class GetInstructions @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {
    operator fun invoke(): Result<List<Instruction>> {
        val instructions = instructionsRepository.getAll()
        return if (instructions.isNotEmpty()) {
            Result.success(instructions)
        } else {
            Result.failure(Exception("There is no instructions"))
        }
    }

}
