package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import com.example.minirobots.instructionsList.domain.entities.*
import javax.inject.Inject

class GetInstructionModifiers @Inject constructor(
    private val instructionsRepository: InstructionsRepository
) {

    operator fun invoke(index: Int): List<Modifier>? {
        return when (instructionsRepository.get(index)?.modifier) {
            is Steps -> return Steps.values().toList()
            is MusicNote -> return MusicNote.values().toList()
            is LedColor -> return LedColor.values().toList()
            is RotationAngle -> return RotationAngle.values().toList()
            else -> null
        }
    }

}