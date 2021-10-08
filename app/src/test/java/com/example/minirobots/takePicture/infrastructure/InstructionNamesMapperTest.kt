package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.instructionsList.domain.entities.Instruction
import com.example.minirobots.takePicture.domain.entities.InstructionName
import junit.framework.Assert.assertEquals
import org.junit.Test

class InstructionNamesMapperTest {

    private val instructionNamesMapper = InstructionNamesMapper()

    @Test
    fun `map empty instruction names list`() {
        val namesList = givenEmptyNamesList()
        val result = whenMappingNamesList(namesList)
        shouldReturnEmptyInstructionList(result)
    }

    @Test
    fun `map single action without modifier`() {
        val namesList = givenSingleActionName()
        val result = whenMappingNamesList(namesList)
//        shouldReturnInstructionWithRandomModifier(result)
    }

    private fun givenEmptyNamesList() = emptyList<InstructionName>()

    private fun givenSingleActionName() = listOf(InstructionName.GIRAR_DERECHA)

    private fun whenMappingNamesList(namesList: List<InstructionName>) = instructionNamesMapper.map(namesList)

    private fun shouldReturnEmptyInstructionList(result: List<Instruction>) = assertEquals(emptyList<Instruction>(), result)


}
