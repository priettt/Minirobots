package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import com.example.minirobots.instructionsList.domain.entities.Instruction
import javax.inject.Inject

class AddInstruction @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(instruction: Instruction) {
        instructionsRepository.add(listOf(instruction))
    }

}
