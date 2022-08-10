package com.example.minirobots.sendInstructions.infrastructure

import com.example.minirobots.common.domain.Action
import com.example.minirobots.common.domain.Instruction
import com.example.minirobots.common.domain.Modifier
import com.example.minirobots.common.infrastructure.repository.FunctionsRepository
import javax.inject.Inject

private const val STEP_DISTANCE = 300

private const val PREFIX = """{"CMD":["""
private const val SUFFIX = "]}"

interface InstructionsParser {
    fun parse(instructions: List<Instruction>): String
}

class InstructionsParserImpl @Inject constructor(
    private val functionsRepository: FunctionsRepository
) : InstructionsParser {
    override fun parse(instructions: List<Instruction>) =
        PREFIX + getParsedInstructions(instructions) + SUFFIX

    private fun getParsedInstructions(instructions: List<Instruction>) = instructions.joinToString {
        parseInstruction(it)
    }

    private fun parseInstruction(instruction: Instruction): String {
        return when (instruction.action) {
            Action.AVANZAR -> """["FD", ${getSteps(instruction.modifier)}]"""
            Action.BAJAR_LAPIZ -> """["PN", 1]"""
            Action.FUNCION -> parseStoredFunction()
            Action.GIRAR_DERECHA -> """["RT", ${getRotationAngle(instruction.modifier)}]"""
            Action.GIRAR_IZQUIERDA -> """["LT", ${getRotationAngle(instruction.modifier)}]"""
            Action.LEDS -> """["LD", ${getLedColor(instruction.modifier)}]"""
            Action.LEVANTAR_LAPIZ -> """["PN", 0]"""
            Action.RETROCEDER -> """["BD", ${getSteps(instruction.modifier)}]"""
            Action.TOCAR_CORCHEA -> """["TE", ${getMusicNote(instruction.modifier)} 60]]"""
            Action.TOCAR_MELODIA -> getMelody(instruction.modifier)
            Action.TOCAR_NEGRA -> """["TE", ${getMusicNote(instruction.modifier)} 120]]"""
            else -> ""
        }
    }

    private fun parseStoredFunction() = functionsRepository.getAll().joinToString {
        parseInstruction(it)
    }

    private fun getMelody(musicNote: Modifier?): String = when (musicNote) {
        Modifier.NOTA_A -> Melodies.Mario
        Modifier.NOTA_B -> Melodies.Mario
        Modifier.NOTA_C -> Melodies.Mario
        Modifier.NOTA_D -> Melodies.Mario
        Modifier.NOTA_E -> Melodies.Mario
        Modifier.NOTA_F -> Melodies.Mario
        Modifier.NOTA_G -> Melodies.Mario
        Modifier.NO_SONIDO -> Melodies.Mario
        else -> Melodies.Mario
    }

    private fun getMusicNote(musicNote: Modifier?) = when (musicNote) {
        Modifier.NOTA_A -> "[440, "
        Modifier.NOTA_B -> "[494, "
        Modifier.NOTA_C -> "[523, "
        Modifier.NOTA_D -> "[587, "
        Modifier.NOTA_E -> "[659, "
        Modifier.NOTA_F -> "[698, "
        Modifier.NOTA_G -> "[784, "
        Modifier.NO_SONIDO -> "[0, "
        else -> listOf(
            "[440, ",
            "[494, ",
            "[523, ",
            "[587, ",
            "[659, ",
            "[698, ",
            "[784, ",
        ).random()
    }

    private fun getSteps(steps: Modifier?) = when (steps) {
        Modifier.NUMERO_2 -> "${STEP_DISTANCE * 2}"
        Modifier.NUMERO_3 -> "${STEP_DISTANCE * 3}"
        Modifier.NUMERO_4 -> "${STEP_DISTANCE * 4}"
        Modifier.NUMERO_5 -> "${STEP_DISTANCE * 5}"
        Modifier.NUMERO_6 -> "${STEP_DISTANCE * 6}"
        else -> "${STEP_DISTANCE * 1}"
    }

    private fun getRotationAngle(modifier: Modifier?) = when (modifier) {
        Modifier.ANGULO_30 -> "30"
        Modifier.ANGULO_36 -> "36"
        Modifier.ANGULO_45 -> "45"
        Modifier.ANGULO_60 -> "60"
        Modifier.ANGULO_72 -> "72"
        Modifier.ANGULO_108 -> "108"
        Modifier.ANGULO_120 -> "120"
        Modifier.ANGULO_135 -> "135"
        Modifier.ANGULO_144 -> "144"
        Modifier.ANGULO_150 -> "150"
        else -> listOf("30", "36", "45", "60", "72", "108", "120", "135", "144", "150").random()
    }

    private fun getLedColor(modifier: Modifier?) = when (modifier) {
        Modifier.COLOR_ROJO -> "[2, 255, 0, 0]"
        Modifier.COLOR_AZUL -> "[2, 0, 0, 255]"
        Modifier.COLOR_VERDE -> "[2, 0, 255, 0]"
        Modifier.COLOR_ROSA -> "[2, 255, 192, 203]"
        Modifier.COLOR_AMARILLO -> "[2, 255, 255, 0]"
        Modifier.COLOR_CELESTE -> "[2, 68, 85, 90]"
        Modifier.NO_COLOR -> "[2, 0, 0, 0]"
        else -> listOf(
            "[2, 255, 0, 0]",
            "[2, 0, 0, 255]",
            "[2, 0, 255, 0]",
            "[2, 255, 192, 203]",
            "[2, 255, 255, 0]",
            "[2, 68, 85, 90]",
            "[2, 255, 255, 255]",
        ).random()
    }

}