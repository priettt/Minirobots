package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.instructionsList.domain.entities.UIAction
import com.example.minirobots.instructionsList.infrastructure.UIActionMapper
import com.example.minirobots.takePicture.infrastructure.CreateInstructionWithRandomModifier
import com.example.minirobots.common.infrastructure.repository.InstructionsRepository
import javax.inject.Inject

class AddInstruction @Inject constructor(
    private val uiActionMapper: UIActionMapper,
    private val instructionsRepository: InstructionsRepository,
    private val createInstructionWithRandomModifier: CreateInstructionWithRandomModifier,
) {

    operator fun invoke(uiAction: UIAction) {
        val action = uiActionMapper.map(uiAction) ?: return
        val instruction = createInstructionWithRandomModifier(action)
        instructionsRepository.add(instruction)
    }

}
