package com.example.minirobots.takePicture.domain

import com.example.minirobots.takePicture.domain.entities.InstructionCardName
import com.example.minirobots.takePicture.infrastructure.LevenshteinDistanceCalculator
import com.example.minirobots.takePicture.infrastructure.StringDistanceInstructionRecognizer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class StringDistanceInstructionRecognizerTest {

    private lateinit var recognizer: StringDistanceInstructionRecognizer

    @Before
    fun setUp() {
        recognizer = StringDistanceInstructionRecognizer(LevenshteinDistanceCalculator())
    }

    @Test
    fun givenSameInstructionString_shouldReturnInstruction() {
        val text = "COLOR AZUL"
        val instructionType = InstructionCardName.COLOR_AZUL
        assertEquals(instructionType, recognizer.getInstructionType(text))
    }

    @Test
    fun givenStringWithDistanceOne_shouldReturnInstruction() {
        val text = "COLOR 4ZUL"
        val instructionType = InstructionCardName.COLOR_AZUL
        assertEquals(instructionType, recognizer.getInstructionType(text))
    }

    @Test
    fun givenStringWithDistanceThree_shouldReturnInstruction() {
        val text = "OR AZUL"
        InstructionCardName.COLOR_AZUL.also {
            assertEquals(recognizer.getInstructionType(text), it)
        }
    }

    @Test
    fun givenStringTooDifferent_shouldReturnNull() {
        val text = "COL"
        assertEquals(null, recognizer.getInstructionType(text))
    }
}