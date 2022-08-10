package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.instructionsList.domain.entities.UIModifier
import com.example.minirobots.instructionsList.infrastructure.UIModifierMapper
import com.example.minirobots.common.infrastructure.repository.InstructionsRepository
import javax.inject.Inject

class EditInstructionModifier @Inject constructor(
    private val instructionsRepository: InstructionsRepository,
    private val uiModifierMapper: UIModifierMapper
) {

    operator fun invoke(index: Int, uiModifier: UIModifier) {
        val modifier = uiModifierMapper.map(uiModifier) ?: return
        instructionsRepository.updateModifier(index, modifier)
    }

}
