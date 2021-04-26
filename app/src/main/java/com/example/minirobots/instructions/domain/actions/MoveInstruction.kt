package com.example.minirobots.instructions.domain.actions

import com.example.minirobots.home.infrastructure.InstructionsRepository
import javax.inject.Inject

class MoveInstruction @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(fromIndex: Int, toIndex: Int) {
        instructionsRepository.move(fromIndex, toIndex)
    }

}
