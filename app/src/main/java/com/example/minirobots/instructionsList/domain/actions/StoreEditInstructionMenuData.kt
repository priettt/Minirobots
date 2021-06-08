package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.instructionsList.domain.entities.Modifier
import com.example.minirobots.instructionsList.infrastructure.EditInstructionMenuData
import com.example.minirobots.instructionsList.infrastructure.EditInstructionMenuDataRepository
import javax.inject.Inject

class StoreEditInstructionMenuData @Inject constructor(
    private val editInstructionMenuDataRepository: EditInstructionMenuDataRepository
) {

    operator fun invoke(instructionIndex: Int, modifiers: List<Modifier>) {
        editInstructionMenuDataRepository.store(EditInstructionMenuData(instructionIndex, modifiers))
    }

}