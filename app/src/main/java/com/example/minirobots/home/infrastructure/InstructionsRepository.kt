package com.example.minirobots.home.infrastructure

import com.example.minirobots.home.domain.Instruction
import javax.inject.Inject

interface InstructionsRepository {
    fun add(instructions: List<Instruction>)
    fun overwrite(instructions: List<Instruction>)
    fun getAll(): List<Instruction>
}

class InMemoryInstructionsRepository @Inject constructor(): InstructionsRepository {

    private var instructions = mutableListOf<Instruction>()

    override fun add(instructions: List<Instruction>) {
        this.instructions.addAll(instructions)
    }

    override fun overwrite(instructions: List<Instruction>) {
        this.instructions = instructions.toMutableList()
    }

    override fun getAll(): List<Instruction> = instructions

}
