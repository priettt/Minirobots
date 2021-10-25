package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.instructionsList.domain.entities.UIModifier
import com.example.minirobots.instructionsList.infrastructure.UIModifierMapper
import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import javax.inject.Inject

class GetInstructionModifiers @Inject constructor(
    private val instructionsRepository: InstructionsRepository,
    private val getAvailableModifiers: GetAvailableModifiers,
    private val uiModifierMapper: UIModifierMapper
) {

    operator fun invoke(index: Int): List<UIModifier> {
        val action = instructionsRepository.get(index)?.action
        val modifiers = getAvailableModifiers(action)
        return modifiers.mapNotNull {
            uiModifierMapper.map(it)
        }
    }

}