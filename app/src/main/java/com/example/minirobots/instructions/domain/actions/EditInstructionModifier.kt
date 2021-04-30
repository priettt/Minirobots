package com.example.minirobots.instructions.domain.actions

import com.example.minirobots.home.infrastructure.InstructionsRepository
import com.example.minirobots.instructions.domain.entities.Modifier
import javax.inject.Inject

class EditInstructionModifier @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(index: Int, modifier: Modifier) {
        instructionsRepository.updateModifier(index, modifier)
    }

}
