package com.example.minirobots.instructions.domain.actions

import com.example.minirobots.home.domain.InstructionContract
import com.example.minirobots.home.infrastructure.InstructionsRepository
import javax.inject.Inject

class GetInstructions @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {
    operator fun invoke(): Result<List<InstructionContract>> {
        val instructions = instructionsRepository.getAll()
        return if (instructions.isNotEmpty()) {
            Result.success(instructions)
        } else {
            Result.failure(Exception("There is no instructions"))
        }
    }

}
