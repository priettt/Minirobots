package com.example.minirobots.instructions.infrastructure

import com.example.minirobots.instructions.domain.entities.Instruction
import com.example.minirobots.instructions.domain.entities.RotateRight
import javax.inject.Inject

class AddInstructionItemsRepository @Inject constructor() {
    fun getAvailableInstructions(): List<Instruction> {
        return listOf(
            RotateRight()
        )
    }
}