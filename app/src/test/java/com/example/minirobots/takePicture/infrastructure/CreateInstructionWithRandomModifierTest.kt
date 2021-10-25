package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.Action
import com.example.minirobots.Instruction
import com.example.minirobots.Modifier
import org.junit.Assert.assertEquals
import org.junit.Test

class CreateInstructionWithRandomModifierTest {

    private val createInstructionWithRandomModifier = CreateInstructionWithRandomModifier()

    private val movementAction = Action.RETROCEDER
    private val turnAction = Action.GIRAR_DERECHA
    private val ledsAction = Action.LEDS
    private val musicAction = Action.TOCAR_NEGRA
    private val nullModifierAction = Action.BAJAR_LAPIZ

    @Test
    fun `movement action`() {
        val result = whenInvokedWithMovementAction()
        shouldReturnActionWithRandomNumber(result)
    }

    @Test
    fun `turn action`() {
        val result = whenInvokedWithTurnAction()
        shouldReturnActionWithRandomAngle(result)
    }

    @Test
    fun `leds action`() {
        val result = whenInvokedWithLedsAction()
        shouldReturnActionWithRandomColor(result)
    }

    @Test
    fun `music action`() {
        val result = whenInvokedWithMusicAction()
        shouldReturnActionWithRandomNote(result)
    }

    @Test
    fun `null modifier action`() {
        val result = whenInvokedWithNullModifierAction()
        shouldReturnActionWithNullModifier(result)
    }

    private fun whenInvokedWithMovementAction(): Instruction {
        return createInstructionWithRandomModifier(movementAction)
    }

    private fun whenInvokedWithTurnAction(): Instruction {
        return createInstructionWithRandomModifier(turnAction)
    }

    private fun whenInvokedWithLedsAction(): Instruction {
        return createInstructionWithRandomModifier(ledsAction)
    }

    private fun whenInvokedWithMusicAction(): Instruction {
        return createInstructionWithRandomModifier(musicAction)
    }

    private fun whenInvokedWithNullModifierAction(): Instruction {
        return createInstructionWithRandomModifier(nullModifierAction)
    }

    private fun shouldReturnActionWithRandomNumber(result: Instruction) {
        assertEquals(movementAction, result.action)
        assertEquals(Modifier.NUMERO_AL_AZAR, result.modifier)
    }

    private fun shouldReturnActionWithRandomAngle(result: Instruction) {
        assertEquals(turnAction, result.action)
        assertEquals(Modifier.ANGULO_AL_AZAR, result.modifier)
    }

    private fun shouldReturnActionWithRandomColor(result: Instruction) {
        assertEquals(ledsAction, result.action)
        assertEquals(Modifier.COLOR_AL_AZAR, result.modifier)
    }

    private fun shouldReturnActionWithRandomNote(result: Instruction) {
        assertEquals(musicAction, result.action)
        assertEquals(Modifier.NOTA_AL_AZAR, result.modifier)
    }

    private fun shouldReturnActionWithNullModifier(result: Instruction) {
        assertEquals(nullModifierAction, result.action)
        assertEquals(null, result.modifier)
    }

}
