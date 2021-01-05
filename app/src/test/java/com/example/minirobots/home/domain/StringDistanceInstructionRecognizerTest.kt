package com.example.minirobots.home.domain

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

internal class StringDistanceInstructionRecognizerTest {

    private lateinit var recognizer: StringDistanceInstructionRecognizer

    @MockK
    lateinit var stringDistanceCalculator: StringDistanceCalculator

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { stringDistanceCalculator.getDistance(any(), any()) } returns 10
        every { stringDistanceCalculator.getDistance(any(), "COLOR AZUL") } returns 0
        recognizer = StringDistanceInstructionRecognizer(stringDistanceCalculator)
    }

    @Test
    fun givenEqualStrings_distanceShouldBeZero() {
        val text = "COLOR AZUL"
        val instructionType = InstructionType.COLOR_AZUL
        assert(recognizer.getInstructionType(text) == instructionType)
    }
}