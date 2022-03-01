package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.Action
import com.example.minirobots.Instruction
import com.example.minirobots.Modifier
import com.example.minirobots.instructionsList.domain.actions.GetAvailableModifiers
import com.example.minirobots.takePicture.domain.entities.PieceName
import com.example.minirobots.takePicture.domain.entities.PieceName.*
import org.junit.Assert.assertEquals
import org.junit.Test

class PieceNamesMapperTest {

    private val pieceNamesMapper =
        PieceNamesMapper(PieceNameMapper(), PieceNameMapperStateMachine(CreateInstructionWithRandomModifier(), GetAvailableModifiers()))

    @Test
    fun `empty piece names list`() {
        val pieceNames = givenEmptyPieceNames()
        val result = whenMappingPieceNames(pieceNames)
        shouldReturnEmptyInstructionList(result)
    }

    @Test
    fun `list with only one modifier`() {
        val namesList = givenSingleModifierName()
        val result = whenMappingPieceNames(namesList)
        shouldReturnEmptyInstructionList(result)
    }

    @Test
    fun `list with only one action`() {
        val namesList = givenSingleActionName()
        val result = whenMappingPieceNames(namesList)
        shouldReturnInstructionWithRandomModifier(result)
    }

    @Test
    fun `list with single piece instruction`() {
        val namesList = givenSinglePieceInstructionName()
        val result = whenMappingPieceNames(namesList)
        shouldReturnSinglePieceInstruction(result)
    }

    @Test
    fun `list with one instruction - action, modifier`() {
        val namesList = givenActionAndModifier()
        val result = whenMappingPieceNames(namesList)
        shouldReturnInstructionWithActionAndModifier(result)
    }

    @Test
    fun `list with two instructions - action, modifier, action, modifier`() {
        val namesList = listOf(GIRAR_DERECHA, ANGULO_30, GIRAR_IZQUIERDA, ANGULO_36)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with two instructions - modifier, action, action, modifier`() {
        val namesList = listOf(ANGULO_30, GIRAR_DERECHA, GIRAR_IZQUIERDA, ANGULO_36)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with two instructions - two actions, two modifiers`() {
        val namesList = listOf(GIRAR_DERECHA, GIRAR_IZQUIERDA, ANGULO_30, ANGULO_36)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_AL_AZAR),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_30),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with two instructions - action, two modifiers, action`() {
        val namesList = listOf(ANGULO_30, GIRAR_DERECHA, GIRAR_IZQUIERDA, ANGULO_36)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with two instructions - two single actions`() {
        val namesList = listOf(BAJAR_LAPIZ, LEVANTAR_LAPIZ)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.BAJAR_LAPIZ, null),
            Instruction(Action.LEVANTAR_LAPIZ, null),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with two instructions - modifier, action, modifier, action`() {
        val namesList = listOf(ANGULO_30, GIRAR_DERECHA, ANGULO_36, GIRAR_IZQUIERDA)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with three instructions - single action, modifier, action, modifier, action`() {
        val namesList = listOf(BAJAR_LAPIZ, ANGULO_30, GIRAR_DERECHA, ANGULO_36, GIRAR_IZQUIERDA)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.BAJAR_LAPIZ, null),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with three instructions - single action, action, modifier, modifier, action`() {
        val namesList = listOf(BAJAR_LAPIZ, GIRAR_DERECHA, ANGULO_30, ANGULO_36, GIRAR_IZQUIERDA)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.BAJAR_LAPIZ, null),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with two instructions - modifier, single action, action, modifier, action`() {
        val namesList = listOf(ANGULO_30, BAJAR_LAPIZ, GIRAR_DERECHA, ANGULO_36, GIRAR_IZQUIERDA)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.BAJAR_LAPIZ, null),
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with two instructions - action, single action`() {
        val namesList = listOf(TOCAR_MELODIA, BAJAR_LAPIZ)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.TOCAR_MELODIA, Modifier.NOTA_AL_AZAR),
            Instruction(Action.BAJAR_LAPIZ, null),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with three instructions - modifier, action, single action, modifier, action`() {
        val namesList = listOf(ANGULO_30, GIRAR_DERECHA, BAJAR_LAPIZ, ANGULO_36, GIRAR_IZQUIERDA)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.BAJAR_LAPIZ, null),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with three instructions - modifier, action, modifier, single action, action`() {
        val namesList = listOf(ANGULO_30, GIRAR_DERECHA, ANGULO_36, BAJAR_LAPIZ, GIRAR_IZQUIERDA)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.BAJAR_LAPIZ, null),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with three instructions - modifier, action, modifier, action, single action`() {
        val namesList = listOf(ANGULO_30, GIRAR_DERECHA, ANGULO_36, GIRAR_IZQUIERDA, BAJAR_LAPIZ)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30),
            Instruction(Action.GIRAR_IZQUIERDA, Modifier.ANGULO_36),
            Instruction(Action.BAJAR_LAPIZ, null),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    @Test
    fun `list with instructions with unrelated modifiers`() {
        val namesList = listOf(ANGULO_30, AVANZAR, TOCAR_MELODIA, LEDS, BAJAR_LAPIZ)
        val result = whenMappingPieceNames(namesList)
        val expected = listOf(
            Instruction(Action.AVANZAR, Modifier.NUMERO_AL_AZAR),
            Instruction(Action.TOCAR_MELODIA, Modifier.NOTA_AL_AZAR),
            Instruction(Action.LEDS, Modifier.COLOR_AL_AZAR),
            Instruction(Action.BAJAR_LAPIZ, null),
        )
        assertInstructionListsAreEquals(expected, result)
    }

    private fun givenEmptyPieceNames() = emptyList<PieceName>()

    private fun givenSingleActionName() = listOf(GIRAR_DERECHA)

    private fun givenSingleModifierName() = listOf(ANGULO_30)

    private fun givenSinglePieceInstructionName() = listOf(BAJAR_LAPIZ)

    private fun givenActionAndModifier() = listOf(GIRAR_DERECHA, ANGULO_30)

    private fun whenMappingPieceNames(namesList: List<PieceName>) =
        pieceNamesMapper.map(namesList)

    private fun shouldReturnEmptyInstructionList(result: List<Instruction>) =
        assertEquals(emptyList<Instruction>(), result)

    private fun shouldReturnSinglePieceInstruction(result: List<Instruction>) =
        assertInstructionListsAreEquals(listOf(Instruction(Action.BAJAR_LAPIZ, null)), result)

    private fun shouldReturnInstructionWithRandomModifier(result: List<Instruction>) {
        val instructionListWithRandomModifier = listOf(Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_AL_AZAR))
        assertInstructionListsAreEquals(instructionListWithRandomModifier, result)
    }

    private fun assertInstructionListsAreEquals(expected: List<Instruction>, result: List<Instruction>) {
        assertEquals(expected.size, result.size)
        expected.forEachIndexed { index, instruction ->
            assertEquals(instruction.action, result.getOrNull(index)?.action)
            assertEquals(instruction.modifier, result.getOrNull(index)?.modifier)
        }
    }

    private fun shouldReturnInstructionWithActionAndModifier(result: List<Instruction>) {
        val instructionWithActionAndModifier = listOf(Instruction(Action.GIRAR_DERECHA, Modifier.ANGULO_30))
        assertInstructionListsAreEquals(instructionWithActionAndModifier, result)
    }

}
