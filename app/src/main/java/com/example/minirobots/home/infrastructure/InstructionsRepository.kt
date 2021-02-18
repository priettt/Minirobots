package com.example.minirobots.home.infrastructure

import com.example.minirobots.home.domain.InstructionContract
import javax.inject.Inject

interface InstructionsRepository {
    fun add(instructions: List<InstructionContract>)
    fun overwrite(instructions: List<InstructionContract>)
    fun getAll(): List<InstructionContract>
}

class InMemoryInstructionsRepository @Inject constructor() : InstructionsRepository {

    private var instructions = mutableListOf<InstructionContract>()

    override fun add(instructions: List<InstructionContract>) {
        this.instructions.addAll(instructions)
    }

    override fun overwrite(instructions: List<InstructionContract>) {
        this.instructions = instructions.toMutableList()
    }

    override fun getAll(): List<InstructionContract> = instructions

}
