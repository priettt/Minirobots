package com.example.minirobots.common.infrastructure.repository

import com.example.minirobots.common.domain.Instruction
import javax.inject.Inject

interface FunctionsRepository {
    fun overwrite(instructions: List<Instruction>)
    fun getAll(): List<Instruction>
}

class InMemoryFunctionsRepository @Inject constructor() : FunctionsRepository {
    private var instructions = mutableListOf<Instruction>()

    override fun overwrite(instructions: List<Instruction>) {
        this.instructions = instructions.toMutableList()
    }

    override fun getAll(): List<Instruction> = instructions

}
