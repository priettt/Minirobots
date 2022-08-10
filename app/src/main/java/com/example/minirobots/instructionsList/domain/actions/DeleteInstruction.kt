package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.common.infrastructure.repository.InstructionsRepository
import javax.inject.Inject

class DeleteInstruction @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(index: Int) {
        instructionsRepository.delete(index)
    }

}
