package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.instructionsList.domain.entities.UIInstruction
import com.example.minirobots.instructionsList.infrastructure.UIInstructionsMapper
import com.example.minirobots.common.infrastructure.repository.InstructionsRepository
import javax.inject.Inject

class GetUIInstructions @Inject constructor(
    private val instructionsRepository: InstructionsRepository,
    private val uiInstructionsMapper: UIInstructionsMapper
) {
    operator fun invoke(): List<UIInstruction> {
        val instructions = instructionsRepository.getAll()
        return uiInstructionsMapper.map(instructions)
    }

}
