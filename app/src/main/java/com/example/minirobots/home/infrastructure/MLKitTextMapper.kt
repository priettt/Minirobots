package com.example.minirobots.home.infrastructure

import com.example.minirobots.home.domain.entities.InstructionCardName
import com.example.minirobots.instructions.domain.entities.*
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class MLKitTextMapper @Inject constructor(
    private val getInstructionCardNames: GetInstructionCardNames,
    private val generateInstructions: GenerateInstructions
) {
    fun getInstructions(mlKitText: Text): List<Instruction> {
        val instructionCardNames = getInstructionCardNames(mlKitText)
        return generateInstructions(instructionCardNames)
    }
}

class GetInstructionCardNames @Inject constructor(
    private val instructionTypeRecognizer: InstructionRecognizer
) {
    operator fun invoke(mlKitText: Text): List<InstructionCardName> {
        val list = mutableListOf<InstructionWithLocation>()
        for (block in mlKitText.textBlocks) {
            for (line in block.lines) {
                instructionTypeRecognizer.getInstructionType(line.text)?.let {
                    list.add(
                        InstructionWithLocation(
                            it,
                            line.cornerPoints?.get(0)?.x ?: 0,
                            line.cornerPoints?.get(0)?.y ?: 0
                        )
                    )
                }
            }
        }
        return list
            .sortedBy { it.x }
            .map { it.type }
    }
}

class GenerateInstructions @Inject constructor() {
    operator fun invoke(instructionCardNames: List<InstructionCardName>): List<Instruction> {
        val instructions = mutableListOf<Instruction>()
        instructionCardNames.forEachIndexed { index, instruction ->
            when (instruction) {
                InstructionCardName.AVANZAR -> instructions.add(MoveForward(getSteps(instructionCardNames, index)))
                InstructionCardName.BAJAR_LAPIZ -> instructions.add(PencilDown())
                InstructionCardName.FUNCION -> instructions.add(FunctionExecute())
                InstructionCardName.FUNCION_COMIENZO -> instructions.add(FunctionStart())
                InstructionCardName.FUNCION_FIN -> instructions.add(FunctionEnd())
                InstructionCardName.GIRAR_DERECHA -> instructions.add(RotateRight(getRotationAngle(instructionCardNames, index)))
                InstructionCardName.GIRAR_IZQUIERDA -> instructions.add(RotateLeft(getRotationAngle(instructionCardNames, index)))
                InstructionCardName.LEDS -> instructions.add(Led(getLedColor(instructionCardNames, index)))
                InstructionCardName.LEVANTAR_LAPIZ -> instructions.add(PencilUp())
                InstructionCardName.PROGRAMA_COMIENZO -> instructions.add(ProgramStart())
                InstructionCardName.PROGRAMA_FIN -> instructions.add(ProgramEnd())
                InstructionCardName.REPETIR_COMIENZO -> instructions.add(RepeatStart(getSteps(instructionCardNames, index)))
                InstructionCardName.REPETIR_FIN -> instructions.add(RepeatEnd())
                InstructionCardName.RETROCEDER -> instructions.add(MoveBackward(getSteps(instructionCardNames, index)))
                InstructionCardName.TOCAR_CORCHEA -> instructions.add(Eighth(getMusicNote(instructionCardNames, index)))
                InstructionCardName.TOCAR_MELODIA -> instructions.add(Melody(getMusicNote(instructionCardNames, index)))
                InstructionCardName.TOCAR_NEGRA -> instructions.add(Quarter(getMusicNote(instructionCardNames, index)))
                else -> { // Do Nothing
                }
            }
        }
        return instructions
    }

    private fun getSteps(instructionCardNames: List<InstructionCardName>, index: Int): Steps {

        fun findSteps(index: Int): Steps? {
            return when (instructionCardNames.getOrNull(index)) {
                InstructionCardName.NUMERO_2 -> return Steps.STEPS_2
                InstructionCardName.NUMERO_3 -> return Steps.STEPS_3
                InstructionCardName.NUMERO_4 -> return Steps.STEPS_4
                InstructionCardName.NUMERO_5 -> return Steps.STEPS_5
                InstructionCardName.NUMERO_6 -> return Steps.STEPS_6
                InstructionCardName.NUMERO_AL_AZAR -> return Steps.RANDOM
                else -> null
            }
        }

        return findSteps(index - 1) ?: findSteps(index + 1) ?: Steps.RANDOM
    }

    private fun getLedColor(instructionCardNames: List<InstructionCardName>, index: Int): LedColor {

        fun findLedColor(index: Int): LedColor? {
            return when (instructionCardNames.getOrNull(index)) {
                InstructionCardName.COLOR_AMARILLO -> LedColor.YELLOW
                InstructionCardName.COLOR_AZUL -> LedColor.BLUE
                InstructionCardName.COLOR_BLANCO -> LedColor.WHITE
                InstructionCardName.COLOR_CIAN -> LedColor.LIGHT_BLUE
                InstructionCardName.COLOR_MAGENTA -> LedColor.PINK
                InstructionCardName.COLOR_ROJO -> LedColor.RED
                InstructionCardName.COLOR_VERDE -> LedColor.GREEN
                InstructionCardName.COLOR_AL_AZAR -> LedColor.RANDOM
                InstructionCardName.NO_COLOR -> LedColor.NONE
                else -> null
            }
        }

        return findLedColor(index - 1) ?: findLedColor(index + 1) ?: LedColor.RANDOM
    }

    private fun getMusicNote(instructionCardNames: List<InstructionCardName>, index: Int): MusicNote {

        fun findNote(index: Int): MusicNote? {
            return when (instructionCardNames.getOrNull(index)) {
                InstructionCardName.NOTA_A -> MusicNote.A
                InstructionCardName.NOTA_B -> MusicNote.B
                InstructionCardName.NOTA_C -> MusicNote.C
                InstructionCardName.NOTA_D -> MusicNote.D
                InstructionCardName.NOTA_E -> MusicNote.E
                InstructionCardName.NOTA_F -> MusicNote.F
                InstructionCardName.NOTA_G -> MusicNote.G
                InstructionCardName.NOTA_AL_AZAR -> MusicNote.RANDOM
                InstructionCardName.NO_SONIDO -> MusicNote.SILENCE
                else -> null
            }
        }

        return findNote(index - 1) ?: findNote(index + 1) ?: MusicNote.RANDOM
    }

    private fun getRotationAngle(instructionCardNames: List<InstructionCardName>, index: Int): RotationAngle {

        fun findRotationAngle(index: Int): RotationAngle? {
            return when (instructionCardNames.getOrNull(index)) {
                InstructionCardName.ANGULO_30 -> RotationAngle.ANGLE_30
                InstructionCardName.ANGULO_36 -> RotationAngle.ANGLE_36
                InstructionCardName.ANGULO_45 -> RotationAngle.ANGLE_45
                InstructionCardName.ANGULO_60 -> RotationAngle.ANGLE_60
                InstructionCardName.ANGULO_72 -> RotationAngle.ANGLE_72
                InstructionCardName.ANGULO_108 -> RotationAngle.ANGLE_108
                InstructionCardName.ANGULO_120 -> RotationAngle.ANGLE_120
                InstructionCardName.ANGULO_135 -> RotationAngle.ANGLE_135
                InstructionCardName.ANGULO_144 -> RotationAngle.ANGLE_144
                InstructionCardName.ANGULO_150 -> RotationAngle.ANGLE_150
                InstructionCardName.ANGULO_AL_AZAR -> RotationAngle.RANDOM
                else -> null
            }
        }
        return findRotationAngle(index - 1) ?: findRotationAngle(index + 1) ?: RotationAngle.RANDOM
    }

}

data class InstructionWithLocation(
    val type: InstructionCardName,
    val x: Int, val y: Int
)