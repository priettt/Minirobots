package com.example.minirobots.instructionsList.infrastructure

import com.example.minirobots.instructionsList.domain.entities.Modifier
import javax.inject.Inject

interface EditInstructionMenuDataRepository {
    fun get(): EditInstructionMenuData
    fun store(editInstructionMenuData: EditInstructionMenuData)
}

class InMemoryEditInstructionMenuDataRepository @Inject constructor() : EditInstructionMenuDataRepository {
    private var editInstructionMenuData = EditInstructionMenuData(-1, listOf())

    override fun get() = editInstructionMenuData
    override fun store(editInstructionMenuData: EditInstructionMenuData) {
        this.editInstructionMenuData = editInstructionMenuData
    }
}

data class EditInstructionMenuData(val instructionIndex: Int, val availableModifiers: List<Modifier>)