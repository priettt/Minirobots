package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import com.example.minirobots.instructionsList.domain.entities.Modifier
import javax.inject.Inject

class EditInstructionModifier @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(index: Int, modifier: Modifier) {
        instructionsRepository.updateModifier(index, modifier)
    }

}
