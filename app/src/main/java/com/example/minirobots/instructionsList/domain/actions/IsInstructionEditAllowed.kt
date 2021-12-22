package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import javax.inject.Inject

class IsInstructionEditAllowed @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    // There could be an instruction that could be edited but its modifier is null. We are not taking that into account, as all the
    // instructions in the list should already account for that.
    operator fun invoke(index: Int): Boolean = instructionsRepository.get(index)?.modifier != null
}
