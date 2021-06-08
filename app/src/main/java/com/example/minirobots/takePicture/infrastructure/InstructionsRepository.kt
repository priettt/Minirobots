package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.instructionsList.domain.entities.Instruction
import com.example.minirobots.instructionsList.domain.entities.Modifier
import javax.inject.Inject

interface InstructionsRepository {
    fun add(instructions: List<Instruction>)
    fun overwrite(instructions: List<Instruction>)
    fun delete(index: Int)
    fun move(originIndex: Int, targetIndex: Int)
    fun getAll(): List<Instruction>
    fun get(index: Int): Instruction?
    fun updateModifier(index: Int, modifier: Modifier)
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

    override fun getAll(): List<Instruction> = instructions.toMutableList()

    override fun get(index: Int): Instruction? {
        return if (index >= 0 && index < instructions.size)
            instructions[index]
        else
            null
    }

    override fun updateModifier(index: Int, modifier: Modifier) {
        if (index >= 0 && index < instructions.size)
            if (instructions[index].modifier != null)
                instructions[index].modifier = modifier
    }

}
