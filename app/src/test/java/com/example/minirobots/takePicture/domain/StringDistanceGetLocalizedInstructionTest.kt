package com.example.minirobots.takePicture.domain

import com.example.minirobots.takePicture.domain.entities.InstructionName
import com.example.minirobots.takePicture.infrastructure.LevenshteinDistanceCalculator
import com.example.minirobots.takePicture.infrastructure.StringDistanceGetLocalizedInstruction
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class StringDistanceGetLocalizedInstructionTest {

    private lateinit var recognizer: StringDistanceGetLocalizedInstruction

    @Before
    fun setUp() {
        recognizer = StringDistanceGetLocalizedInstruction(LevenshteinDistanceCalculator())
    }

    @Test
    fun givenSameInstructionString_shouldReturnInstruction() {
        val text = "COLOR AZUL"
        val instructionType = InstructionName.COLOR_AZUL
        assertEquals(instructionType, recognizer.getInstructionType(text))
    }

    @Test
    fun givenStringWithDistanceOne_shouldReturnInstruction() {
        val text = "COLOR 4ZUL"
        val instructionType = InstructionName.COLOR_AZUL
        assertEquals(instructionType, recognizer.getInstructionType(text))
    }

    @Test
    fun givenStringWithDistanceThree_shouldReturnInstruction() {
        val text = "OR AZUL"
        InstructionName.COLOR_AZUL.also {
            assertEquals(recognizer.getInstructionType(text), it)
        }
    }

    @Test
    fun givenStringTooDifferent_shouldReturnNull() {
        val text = "COL"
        assertEquals(null, recognizer.getInstructionType(text))
    }
}