package com.example.minirobots.instructions.domain.actions

import com.example.minirobots.home.infrastructure.InstructionsRepository
import com.example.minirobots.instructions.domain.entities.Instruction
import javax.inject.Inject

class DeleteInstruction @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(index: Int) {
        instructionsRepository.delete(index)
    }

}
