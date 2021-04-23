package com.example.minirobots.instructions.domain.actions

import com.example.minirobots.home.infrastructure.InstructionsRepository
import com.example.minirobots.instructions.domain.entities.Instruction
import javax.inject.Inject

class AddInstruction @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(instruction: Instruction) {
        instructionsRepository.add(listOf(instruction))
    }

}
