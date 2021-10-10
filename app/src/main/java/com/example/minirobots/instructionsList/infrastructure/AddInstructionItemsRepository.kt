package com.example.minirobots.instructionsList.infrastructure

import com.example.minirobots.instructionsList.domain.entities.*
import javax.inject.Inject

class AddInstructionItemsRepository @Inject constructor() {
    fun getAvailableInstructions(): List<UIInstruction> {
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