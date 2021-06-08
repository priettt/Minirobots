package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.instructionsList.domain.entities.Instruction
import com.example.minirobots.instructionsList.infrastructure.AddInstructionItemsRepository
import javax.inject.Inject

class GetAvailableInstructions @Inject constructor(
    private val availableInstructionsRepository: AddInstructionItemsRepository
) {

    operator fun invoke(): List<Instruction> {
        return availableInstructionsRepository.getAvailableInstructions()
    }

}
