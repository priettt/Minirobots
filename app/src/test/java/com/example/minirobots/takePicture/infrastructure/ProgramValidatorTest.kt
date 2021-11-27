package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.Action
import com.example.minirobots.Instruction
import com.example.minirobots.Modifier
import com.example.minirobots.sendInstructions.infrastructure.ProgramValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProgramValidatorTest {

    private var programValidator = ProgramValidator()

    @Test
    fun `program with correct start and end`() {
        val program = givenAProgramWithAStartAndEndAction()
        val result = whenValidatingProgram(program)
        assertTrue(result)
    }

    @Test
    fun `program without end`() {
        val program = givenAProgramWithoutAnEnd()
        val result = whenValidatingProgram(program)
        assertFalse(result)
    }

    @Test
    fun `program without start`() {
        val program = givenAProgramWithoutAStart()
        val result = whenValidatingProgram(program)
        assertFalse(result)
    }

    @Test
    fun `empty program`() {
        val program = givenAnEmptyProgram()
        val result = whenValidatingProgram(program)
        assertFalse(result)
    }

    private fun givenAnEmptyProgram(): List<Instruction> {
        return emptyList()
    }

    private fun givenAProgramWithoutAStart(): List<Instruction> {
        return listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
            Instruction(Action.PROGRAMA_FIN, null),
        )
    }

    private fun givenAProgramWithoutAnEnd(): List<Instruction> {
        return listOf(
            Instruction(Action.PROGRAMA_COMIENZO, null),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36)
        )
    }

    private fun givenAProgramWithAStartAndEndAction(): List<Instruction> {
        return listOf(
            Instruction(Action.PROGRAMA_COMIENZO, null),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
            Instruction(Action.PROGRAMA_FIN, null),
        )
    }

    private fun whenValidatingProgram(program: List<Instruction>) = programValidator.isProgramValid(program)
}