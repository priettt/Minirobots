package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.common.infrastructure.repository.InstructionsRepository
import javax.inject.Inject

class MoveInstruction @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(fromIndex: Int, toIndex: Int) {
        instructionsRepository.move(fromIndex, toIndex)
    }

}
