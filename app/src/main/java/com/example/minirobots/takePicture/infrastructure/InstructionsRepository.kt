package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.Instruction
import com.example.minirobots.Modifier

import javax.inject.Inject

interface InstructionsRepository {
    fun add(instruction: Instruction)
    fun overwrite(instructions: List<Instruction>)
    fun delete(index: Int)
    fun move(originIndex: Int, targetIndex: Int)
    fun getAll(): List<Instruction>
    fun get(index: Int): Instruction?
    fun updateModifier(index: Int, modifier: Modifier)
}

class InMemoryInstructionsRepository @Inject constructor() : InstructionsRepository {

    private var instructions = mutableListOf<Instruction>()

    override fun add(instruction: Instruction) {
        instructions.add(instruction)
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

    override fun get(index: Int) = instructions.getOrNull(index)

    override fun updateModifier(index: Int, modifier: Modifier) {
        val instruction = instructions.getOrNull(index) ?: return
        instructions[index] = Instruction(instruction.action, modifier, instruction.id)
    }

}
