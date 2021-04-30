package com.example.minirobots.instructions.domain.actions

import com.example.minirobots.instructions.infrastructure.EditInstructionMenuData
import com.example.minirobots.instructions.infrastructure.EditInstructionMenuDataRepository
import javax.inject.Inject

class GetEditInstructionMenuData @Inject constructor(
    private val editInstructionMenuDataRepository: EditInstructionMenuDataRepository
) {

    operator fun invoke(): EditInstructionMenuData = editInstructionMenuDataRepository.get()

}