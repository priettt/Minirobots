package com.example.minirobots.sendInstructions.infrastructure

import com.example.minirobots.instructionsList.domain.entities.*
import javax.inject.Inject

private const val STEP_DISTANCE = 30

interface InstructionsParser {
    fun parse(instructions: List<UIInstruction>): String
}

class InstructionsParserImpl @Inject constructor() : InstructionsParser {
    override fun parse(instructions: List<UIInstruction>) =
        getPrefix() + getParsedInstructions(instructions) + getSuffix()

    private fun getParsedInstructions(instructions: List<UIInstruction>): String {
        return instructions.joinToString {
            parseInstruction(it)
        }
    }

    private fun parseInstruction(instruction: UIInstruction): String {
        return when (instruction) {
            is Eighth -> """["TE", ${getMusicNote(instruction.modifier as MusicNote)} 60]]"""
            is FunctionEnd -> ""
            is FunctionExecute -> ""
            is FunctionStart -> ""
            is Led -> """["LD", ${getLedColor(instruction.modifier as LedColor)}]"""
            is Melody -> getMelody(instruction.modifier as MusicNote)
            is MoveBackward -> """["BD", ${getSteps(instruction.modifier as Steps)}]"""
            is MoveForward -> """["FD", ${getSteps(instruction.modifier as Steps)}]"""
            is PencilDown -> """["PN", 1]"""
            is PencilUp -> """["PN", 0]"""
            is ProgramEnd -> ""
            is ProgramStart -> ""
            is Quarter -> """["TE", ${getMusicNote(instruction.modifier as MusicNote)} 120]]"""
            is RepeatEnd -> ""
            is RepeatStart -> ""
            is RotateLeft -> """["LT", ${getRotationAngle(instruction.modifier as RotationAngle)}]"""
            is RotateRight -> """["RT", ${getRotationAngle(instruction.modifier as RotationAngle)}]"""
        }
    }

    private fun getMelody(musicNote: MusicNote): String = when (musicNote) {
        MusicNote.A -> Melodies.Mario
        MusicNote.B -> Melodies.Mario
        MusicNote.C -> Melodies.Mario
        MusicNote.D -> Melodies.Mario
        MusicNote.E -> Melodies.Mario
        MusicNote.F -> Melodies.Mario
        MusicNote.G -> Melodies.Mario
        MusicNote.SILENCE -> Melodies.Mario
        MusicNote.RANDOM -> Melodies.Mario
    }

    private fun getMusicNote(musicNote: MusicNote) = when (musicNote) {
        MusicNote.A -> "[440, "
        MusicNote.B -> "[494, "
        MusicNote.C -> "[523, "
        MusicNote.D -> "[587, "
        MusicNote.E -> "[659, "
        MusicNote.F -> "[698, "
        MusicNote.G -> "[784, "
        MusicNote.SILENCE -> "[0, "
        MusicNote.RANDOM -> listOf(
            "[440, ",
            "[494, ",
            "[523, ",
            "[587, ",
            "[659, ",
            "[698, ",
            "[784, ",
        ).random()
    }

    private fun getSteps(steps: Steps) = when (steps) {
        Steps.STEPS_2 -> "${STEP_DISTANCE * 2}"
        Steps.STEPS_3 -> "${STEP_DISTANCE * 3}"
        Steps.STEPS_4 -> "${STEP_DISTANCE * 4}"
        Steps.STEPS_5 -> "${STEP_DISTANCE * 5}"
        Steps.STEPS_6 -> "${STEP_DISTANCE * 6}"
        Steps.RANDOM -> listOf(
            "${STEP_DISTANCE * 2}",
            "${STEP_DISTANCE * 3}",
            "${STEP_DISTANCE * 4}",
            "${STEP_DISTANCE * 5}",
            "${STEP_DISTANCE * 6}"
        ).random()
    }

    private fun getRotationAngle(modifier: RotationAngle) = when (modifier) {
        RotationAngle.ANGLE_30 -> "30"
        RotationAngle.ANGLE_36 -> "36"
        RotationAngle.ANGLE_45 -> "45"
        RotationAngle.ANGLE_60 -> "60"
        RotationAngle.ANGLE_72 -> "72"
        RotationAngle.ANGLE_108 -> "108"
        RotationAngle.ANGLE_120 -> "120"
        RotationAngle.ANGLE_135 -> "135"
        RotationAngle.ANGLE_144 -> "144"
        RotationAngle.ANGLE_150 -> "150"
        RotationAngle.RANDOM -> listOf("30", "36", "45", "60", "72", "108", "120", "135", "144", "150").random()
    }

    private fun getLedColor(modifier: LedColor) = when (modifier) {
        LedColor.RED -> "[2, 255, 0, 0]"
        LedColor.BLUE -> "[2, 0, 0, 255]"
        LedColor.GREEN -> "[2, 0, 255, 0]"
        LedColor.PINK -> "[2, 255, 192, 203]"
        LedColor.YELLOW -> "[2, 255, 255, 0]"
        LedColor.LIGHT_BLUE -> "[2, 68, 85, 90]"
        LedColor.WHITE -> "[2, 255, 255, 255]"
        LedColor.RANDOM -> listOf(
            "[2, 255, 0, 0]",
            "[2, 0, 0, 255]",
            "[2, 0, 255, 0]",
            "[2, 255, 192, 203]",
            "[2, 255, 255, 0]",
            "[2, 68, 85, 90]",
            "[2, 255, 255, 255]",
        ).random()
        LedColor.NONE -> "[2, 0, 0, 0]"
    }

    private fun getPrefix() = """{"CMD":["""
    private fun getSuffix() = "]}"

}