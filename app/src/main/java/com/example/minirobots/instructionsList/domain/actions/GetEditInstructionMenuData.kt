package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.instructionsList.infrastructure.EditInstructionMenuData
import com.example.minirobots.instructionsList.infrastructure.EditInstructionMenuDataRepository
import javax.inject.Inject

class GetEditInstructionMenuData @Inject constructor(
    private val editInstructionMenuDataRepository: EditInstructionMenuDataRepository
) {

    operator fun invoke(): EditInstructionMenuData = editInstructionMenuDataRepository.get()

}