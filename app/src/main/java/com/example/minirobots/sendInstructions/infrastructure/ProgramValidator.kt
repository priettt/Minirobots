package com.example.minirobots.sendInstructions.infrastructure

import com.example.minirobots.Action
import com.example.minirobots.Instruction
import javax.inject.Inject

class ProgramValidator @Inject constructor() {
    fun isProgramValid(instructions: List<Instruction>): Boolean {
        return instructions.getOrNull(0)?.action == Action.PROGRAMA_COMIENZO
                && instructions.lastOrNull()?.action == Action.PROGRAMA_FIN
    }
}
