package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.instructionsList.domain.entities.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

val LIST_OF_INSTRUCTIONS = listOf(ProgramStart(), MoveForward(), Led(), MoveBackward(), ProgramEnd())

class InMemoryInstructionsRepositoryTest {

    var instructionsRepository = InMemoryInstructionsRepository()

    @Before
    fun setUp() {
        instructionsRepository = InMemoryInstructionsRepository()
        instructionsRepository.add(LIST_OF_INSTRUCTIONS)
    }

    @Test
    fun `move instruction should leave the origin instruction at the target index`() {
        val instructionAtIndexZero = LIST_OF_INSTRUCTIONS[0]
        instructionsRepository.move(0, 1)
        assertEquals(instructionAtIndexZero, instructionsRepository.getAll()[1])
    }

    @Test
    fun `move instruction forward should update index positions`() {
        val expectedList = listOf(ProgramStart(), Led(), MoveBackward(), MoveForward(), ProgramEnd())
        instructionsRepository.move(1, 3)
        assertEquals(expectedList, instructionsRepository.getAll())
    }

    @Test
    fun `move instruction backwards should update index positions`() {
        val expectedList = listOf(ProgramStart(), MoveBackward(), MoveForward(), Led(), ProgramEnd())
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