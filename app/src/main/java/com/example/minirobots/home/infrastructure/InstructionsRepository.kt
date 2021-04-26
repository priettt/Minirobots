package com.example.minirobots.home.infrastructure

import com.example.minirobots.instructions.domain.entities.Instruction
import javax.inject.Inject

interface InstructionsRepository {
    fun add(instructions: List<Instruction>)
    fun overwrite(instructions: List<Instruction>)
    fun delete(index: Int)
    fun move(fromIndex: Int, toIndex: Int)
    fun getAll(): List<Instruction>
}

class InMemoryInstructionsRepository @Inject constructor() : InstructionsRepository {

    private var instructions = mutableListOf<Instruction>()

    override fun add(instructions: List<Instruction>) {
        this.instructions.addAll(instructions)
    }

    override fun overwrite(instructions: List<Instruction>) {
        this.instructions = instructions.toMutableList()
    }

    override fun delete(index: Int) {
        if (index >= 0 && index < instructions.size)
            instructions.removeAt(index)
    }

    override fun move(originIndex: Int, targetIndex: Int) {
        val originInstruction = instructions[originIndex]
        instructions.removeAt(originIndex)
        instructions.add(targetIndex, originInstruction)
    }

    override fun getAll(): List<Instruction> = instructions

}
