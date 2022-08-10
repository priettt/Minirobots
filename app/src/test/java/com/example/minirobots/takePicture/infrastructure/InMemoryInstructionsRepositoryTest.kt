package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.common.domain.Action
import com.example.minirobots.common.domain.Instruction
import com.example.minirobots.common.domain.Modifier
import com.example.minirobots.common.infrastructure.repository.InMemoryInstructionsRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

private val testUUID = UUID.randomUUID()

val LIST_OF_INSTRUCTIONS = listOf(
    Instruction(Action.AVANZAR, Modifier.NUMERO_2, testUUID),
    Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_144, testUUID),
    Instruction(Action.LEVANTAR_LAPIZ, null, testUUID),
    Instruction(Action.RETROCEDER, Modifier.NUMERO_3, testUUID),
)

class InMemoryInstructionsRepositoryTest {

    private var instructionsRepository = InMemoryInstructionsRepository()

    @Before
    fun setUp() {
        instructionsRepository = InMemoryInstructionsRepository()
        LIST_OF_INSTRUCTIONS.forEach { instructionsRepository.add(it) }
    }

    @Test
    fun `move instruction should leave the origin instruction at the target index`() {
        val instructionAtIndexZero = LIST_OF_INSTRUCTIONS[0]
        instructionsRepository.move(0, 1)
        assertEquals(instructionAtIndexZero, instructionsRepository.getAll()[1])
    }

    @Test
    fun `move instruction forward should update index positions`() {
        val expectedList = listOf(
            Instruction(Action.AVANZAR, Modifier.NUMERO_2, testUUID),
            Instruction(Action.LEVANTAR_LAPIZ, null, testUUID),
            Instruction(Action.RETROCEDER, Modifier.NUMERO_3, testUUID),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_144, testUUID),
        )
        instructionsRepository.move(1, 3)
        assertEquals(expectedList, instructionsRepository.getAll())
    }

    @Test
    fun `move instruction backwards should update index positions`() {
        val expectedList = listOf(
            Instruction(Action.AVANZAR, Modifier.NUMERO_2, testUUID),
            Instruction(Action.RETROCEDER, Modifier.NUMERO_3, testUUID),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_144, testUUID),
            Instruction(Action.LEVANTAR_LAPIZ, null, testUUID),
        )
        instructionsRepository.move(3, 1)
        assertEquals(expectedList, instructionsRepository.getAll())
    }

    @Test
    fun `move instruction to the same place should not change index positions`() {
        val expectedList = LIST_OF_INSTRUCTIONS
        instructionsRepository.move(0, 0)
        assertEquals(expectedList, instructionsRepository.getAll())
    }

}