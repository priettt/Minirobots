package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.Instruction
import com.example.minirobots.instructionsList.domain.entities.Modifier
import com.example.minirobots.instructionsList.domain.entities.UIInstruction
import javax.inject.Inject

interface InstructionsRepository {
    fun add(instructions: List<UIInstruction>)
    fun overwrite(instructions: List<UIInstruction>)
    fun overwrite2(instructions: List<Instruction>)
    fun delete(index: Int)
    fun move(originIndex: Int, targetIndex: Int)
    fun getAll(): List<UIInstruction>
    fun get(index: Int): UIInstruction?
    fun updateModifier(index: Int, modifier: Modifier)
}

class InMemoryInstructionsRepository @Inject constructor() : InstructionsRepository {

    private var instructions = mutableListOf<UIInstruction>()
    private var instructions2 = mutableListOf<Instruction>()

    override fun add(instructions: List<UIInstruction>) {
        this.instructions.addAll(instructions)
    }

    override fun overwrite(instructions: List<UIInstruction>) {
        this.instructions = instructions.toMutableList()
    }

    override fun overwrite2(instructions: List<Instruction>) {
        this.instructions2 = instructions.toMutableList()
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

    override fun getAll(): List<UIInstruction> = instructions.toMutableList()

    override fun get(index: Int): UIInstruction? {
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
