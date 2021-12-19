package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.Instruction
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
