package com.example.minirobots.instructions.domain.actions

import com.example.minirobots.instructions.domain.entities.Modifier
import com.example.minirobots.instructions.infrastructure.EditInstructionMenuData
import com.example.minirobots.instructions.infrastructure.EditInstructionMenuDataRepository
import javax.inject.Inject

class StoreEditInstructionMenuData @Inject constructor(
    private val editInstructionMenuDataRepository: EditInstructionMenuDataRepository
) {

    operator fun invoke(instructionIndex: Int, modifiers: List<Modifier>) {
        editInstructionMenuDataRepository.store(EditInstructionMenuData(instructionIndex, modifiers))
    }

}