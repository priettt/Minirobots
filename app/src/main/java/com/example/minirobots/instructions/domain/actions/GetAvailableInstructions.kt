package com.example.minirobots.instructions.domain.actions

import com.example.minirobots.instructions.domain.entities.Instruction
import com.example.minirobots.instructions.infrastructure.AddInstructionItemsRepository
import javax.inject.Inject

class GetAvailableInstructions @Inject constructor(
    private val availableInstructionsRepository: AddInstructionItemsRepository
) {

    operator fun invoke(): List<Instruction> {
        return availableInstructionsRepository.getAvailableInstructions()
    }

}
