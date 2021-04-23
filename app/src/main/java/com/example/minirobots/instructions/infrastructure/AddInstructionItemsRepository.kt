package com.example.minirobots.instructions.infrastructure

import com.example.minirobots.instructions.domain.entities.*
import javax.inject.Inject

class AddInstructionItemsRepository @Inject constructor() {
    fun getAvailableInstructions(): List<Instruction> {
        return listOf(
            RotateRight(),
            RotateLeft(),
            Led(),
            MoveForward(),
            MoveBackward(),
            RepeatStart(),
            Quarter(),
            Eighth(),
            Melody(),
            ProgramStart(),
            ProgramEnd(),
            RepeatEnd(),
            FunctionStart(),
            FunctionEnd(),
            FunctionExecute(),
            PencilDown(),
            PencilUp()
        )
    }
}