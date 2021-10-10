package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.instructionsList.domain.entities.UIInstruction
import com.example.minirobots.instructionsList.infrastructure.AddInstructionItemsRepository
import javax.inject.Inject

class GetAvailableInstructions @Inject constructor(
    private val availableInstructionsRepository: AddInstructionItemsRepository
) {

    operator fun invoke(): List<UIInstruction> {
        return availableInstructionsRepository.getAvailableInstructions()
    }

}
