package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.takePicture.domain.entities.InstructionName
import com.example.minirobots.instructionsList.domain.entities.*
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class MLKitTextMapper @Inject constructor(
    private val getRecognizedInstructionNames: GetRecognizedInstructionNames,
    private val generateInstructions: GenerateInstructions
) {
    fun map(mlKitText: Text): List<Instruction> {
        val instructionCardNames = getRecognizedInstructionNames(mlKitText)
        return generateInstructions(instructionCardNames)
    }
}


class GenerateInstructions @Inject constructor() {
    operator fun invoke(instructionNames: List<InstructionName>): List<Instruction> {
        val instructions = mutableListOf<Instruction>()
        instructionNames.forEachIndexed { index, instruction ->
            when (instruction) {
                InstructionName.AVANZAR -> instructions.add(MoveForward(modifier = getSteps(instructionNames, index)))
                InstructionName.BAJAR_LAPIZ -> instructions.add(PencilDown())
                InstructionName.FUNCION -> instructions.add(FunctionExecute())
                InstructionName.FUNCION_COMIENZO -> instructions.add(FunctionStart())
                InstructionName.FUNCION_FIN -> instructions.add(FunctionEnd())
                InstructionName.GIRAR_DERECHA -> instructions.add(RotateRight(modifier = getAngle(instructionNames, index)))
                InstructionName.GIRAR_IZQUIERDA -> instructions.add(RotateLeft(modifier = getAngle(instructionNames, index)))
                InstructionName.LEDS -> instructions.add(Led(modifier = getLedColor(instructionNames, index)))
                InstructionName.LEVANTAR_LAPIZ -> instructions.add(PencilUp())
                InstructionName.PROGRAMA_COMIENZO -> instructions.add(ProgramStart())
                InstructionName.PROGRAMA_FIN -> instructions.add(ProgramEnd())
                InstructionName.REPETIR_COMIENZO -> instructions.add(RepeatStart(modifier = getSteps(instructionNames, index)))
                InstructionName.REPETIR_FIN -> instructions.add(RepeatEnd())
                InstructionName.RETROCEDER -> instructions.add(MoveBackward(modifier = getSteps(instructionNames, index)))
                InstructionName.TOCAR_CORCHEA -> instructions.add(Eighth(modifier = getMusicNote(instructionNames, index)))
                InstructionName.TOCAR_MELODIA -> instructions.add(Melody(modifier = getMusicNote(instructionNames, index)))
                InstructionName.TOCAR_NEGRA -> instructions.add(Quarter(modifier = getMusicNote(instructionNames, index)))
                else -> { // Do Nothing
                }
            }
        }
        return instructions
    }

    private fun getSteps(instructionNames: List<InstructionName>, index: Int): Steps {

        fun findSteps(index: Int): Steps? {
            return when (instructionNames.getOrNull(index)) {
                InstructionName.NUMERO_2 -> return Steps.STEPS_2
                InstructionName.NUMERO_3 -> return Steps.STEPS_3
                InstructionName.NUMERO_4 -> return Steps.STEPS_4
                InstructionName.NUMERO_5 -> return Steps.STEPS_5
                InstructionName.NUMERO_6 -> return Steps.STEPS_6
                InstructionName.NUMERO_AL_AZAR -> return Steps.RANDOM
                else -> null
            }
        }

        return findSteps(index - 1) ?: findSteps(index + 1) ?: Steps.RANDOM
    }

    private fun getLedColor(instructionNames: List<InstructionName>, index: Int): LedColor {

        fun findLedColor(index: Int): LedColor? {
            return when (instructionNames.getOrNull(index)) {
                InstructionName.COLOR_AMARILLO -> LedColor.YELLOW
                InstructionName.COLOR_AZUL -> LedColor.BLUE
                InstructionName.COLOR_BLANCO -> LedColor.WHITE
                InstructionName.COLOR_CIAN -> LedColor.LIGHT_BLUE
                InstructionName.COLOR_MAGENTA -> LedColor.PINK
                InstructionName.COLOR_ROJO -> LedColor.RED
                InstructionName.COLOR_VERDE -> LedColor.GREEN
                InstructionName.COLOR_AL_AZAR -> LedColor.RANDOM
                InstructionName.NO_COLOR -> LedColor.NONE
                else -> null
            }
        }

        return findLedColor(index - 1) ?: findLedColor(index + 1) ?: LedColor.RANDOM
    }

    private fun getMusicNote(instructionNames: List<InstructionName>, index: Int): MusicNote {

        fun findNote(index: Int): MusicNote? {
            return when (instructionNames.getOrNull(index)) {
                InstructionName.NOTA_A -> MusicNote.A
                InstructionName.NOTA_B -> MusicNote.B
                InstructionName.NOTA_C -> MusicNote.C
                InstructionName.NOTA_D -> MusicNote.D
                InstructionName.NOTA_E -> MusicNote.E
                InstructionName.NOTA_F -> MusicNote.F
                InstructionName.NOTA_G -> MusicNote.G
                InstructionName.NOTA_AL_AZAR -> MusicNote.RANDOM
                InstructionName.NO_SONIDO -> MusicNote.SILENCE
                else -> null
            }
        }

        return findNote(index - 1) ?: findNote(index + 1) ?: MusicNote.RANDOM
    }

    private fun getAngle(instructionNames: List<InstructionName>, index: Int): RotationAngle {

        fun findRotationAngle(index: Int): RotationAngle? {
            return when (instructionNames.getOrNull(index)) {
                InstructionName.ANGULO_30 -> RotationAngle.ANGLE_30
                InstructionName.ANGULO_36 -> RotationAngle.ANGLE_36
                InstructionName.ANGULO_45 -> RotationAngle.ANGLE_45
                InstructionName.ANGULO_60 -> RotationAngle.ANGLE_60
                InstructionName.ANGULO_72 -> RotationAngle.ANGLE_72
                InstructionName.ANGULO_108 -> RotationAngle.ANGLE_108
                InstructionName.ANGULO_120 -> RotationAngle.ANGLE_120
                InstructionName.ANGULO_135 -> RotationAngle.ANGLE_135
                InstructionName.ANGULO_144 -> RotationAngle.ANGLE_144
                InstructionName.ANGULO_150 -> RotationAngle.ANGLE_150
                InstructionName.ANGULO_AL_AZAR -> RotationAngle.RANDOM
                else -> null
            }
        }
        return findRotationAngle(index - 1) ?: findRotationAngle(index + 1) ?: RotationAngle.RANDOM
    }

}

data class LocalizedInstruction(
    val type: InstructionName,
    val x: Int, val y: Int
)