package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import com.example.minirobots.instructionsList.domain.entities.UIInstruction
import javax.inject.Inject

class AddInstruction @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(instruction: UIInstruction) {
        instructionsRepository.add(listOf(instruction))
    }

}
