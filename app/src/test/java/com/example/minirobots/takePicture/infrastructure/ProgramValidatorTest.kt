package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.common.domain.Action
import com.example.minirobots.common.domain.Instruction
import com.example.minirobots.common.domain.Modifier
import com.example.minirobots.sendInstructions.infrastructure.ProgramValidationState
import com.example.minirobots.sendInstructions.infrastructure.ProgramValidator
import com.example.minirobots.common.infrastructure.repository.FunctionsRepository
import com.example.minirobots.common.infrastructure.repository.InstructionsRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgramValidatorTest {

    private var mockInstructionsRepository = mockk<InstructionsRepository>(relaxed = true)
    private var mockFunctionsRepository = mockk<FunctionsRepository>(relaxed = true)

    private var programValidator = ProgramValidator(mockInstructionsRepository, mockFunctionsRepository)

    @Test
    fun `program with correct start and end`() {
        givenAProgramWithAStartAndEndAction()
        val result = whenValidatingProgram()
        assertEquals(ProgramValidationState.Valid, result)
    }

    @Test
    fun `program without end`() {
        givenAProgramWithoutAnEnd()
        val result = whenValidatingProgram()
        assertEquals(ProgramValidationState.IncorrectStartAndEndInstructions, result)
    }

    @Test
    fun `program without start`() {
        givenAProgramWithoutAStart()
        val result = whenValidatingProgram()
        assertEquals(ProgramValidationState.IncorrectStartAndEndInstructions, result)
    }

    @Test
    fun `function with correct start and end`() {
        givenAFunctionWithAStartAndEndAction()
        val result = whenValidatingProgram()
        assertEquals(ProgramValidationState.Valid, result)
    }

    @Test
    fun `function without end`() {
        givenAFunctionWithoutAnEnd()
        val result = whenValidatingProgram()
        assertEquals(ProgramValidationState.IncorrectStartAndEndInstructions, result)
    }

    @Test
    fun `function without start`() {
        givenAFunctionWithoutAStart()
        val result = whenValidatingProgram()
        assertEquals(ProgramValidationState.IncorrectStartAndEndInstructions, result)
    }

    @Test
    fun `program with a function and function is not stored`() {
        givenAProgramWithAFunctionInstruction()
        givenThereIsNoFunctionStored()
        val result = whenValidatingProgram()
        assertEquals(ProgramValidationState.FunctionNotStored, result)
    }

    @Test
    fun `program with a function and function is stored`() {
        givenAProgramWithAFunctionInstruction()
        givenThereIsAFunctionStored()
        val result = whenValidatingProgram()
        assertEquals(ProgramValidationState.Valid, result)
    }

    @Test
    fun `empty program`() {
        givenAnEmptyProgram()
        val result = whenValidatingProgram()
        assertEquals(ProgramValidationState.EmptyProgram, result)
    }

    private fun givenAnEmptyProgram(): List<Instruction> {
        return emptyList()
    }

    private fun givenAProgramWithoutAStart() {
        every { mockInstructionsRepository.getAll() } returns listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
            Instruction(Action.PROGRAMA_FIN, null),
        )
    }

    private fun givenAProgramWithoutAnEnd() {
        every { mockInstructionsRepository.getAll() } returns listOf(
            Instruction(Action.PROGRAMA_COMIENZO, null),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36)
        )
    }

    private fun givenAProgramWithAStartAndEndAction() {
        every { mockInstructionsRepository.getAll() } returns listOf(
            Instruction(Action.PROGRAMA_COMIENZO, null),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
            Instruction(Action.PROGRAMA_FIN, null),
        )
    }

    private fun givenAFunctionWithoutAStart() {
        every { mockInstructionsRepository.getAll() } returns listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
            Instruction(Action.FUNCION_FIN, null),
        )
    }

    private fun givenAFunctionWithoutAnEnd() {
        every { mockInstructionsRepository.getAll() } returns listOf(
            Instruction(Action.FUNCION_COMIENZO, null),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36)
        )
    }

    private fun givenAFunctionWithAStartAndEndAction() {
        every { mockInstructionsRepository.getAll() } returns listOf(
            Instruction(Action.FUNCION_COMIENZO, null),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
            Instruction(Action.FUNCION_FIN, null),
        )
    }

    private fun givenAProgramWithAFunctionInstruction() {
        every { mockInstructionsRepository.getAll() } returns listOf(
            Instruction(Action.FUNCION_COMIENZO, null),
            Instruction(Action.FUNCION, null),
            Instruction(Action.FUNCION_FIN, null),
        )
    }

    private fun givenThereIsNoFunctionStored() {
        every { mockFunctionsRepository.getAll() } returns emptyList()
    }

    private fun givenThereIsAFunctionStored() {
        every { mockFunctionsRepository.getAll() } returns listOf(
            Instruction(Action.FUNCION_COMIENZO, null)
        )
    }

    private fun whenValidatingProgram() = programValidator.getProgramValidationState()
}