package com.example.minirobots.home.domain

import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class MLKitTextMapper @Inject constructor(
    private val instructionTypeRecognizer: InstructionRecognizer
) {
    fun getInstructions(mlKitText: Text): List<InstructionContract> {

        val listOfInstructionsWithLocation = mutableListOf<InstructionWithLocation>()

        fun findLedColor(index: Int): LedColor? {
            return when (listOfInstructionsWithLocation.getOrNull(index)?.type) {
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

        fun findNote(index: Int): MusicNote? {
            return when (listOfInstructionsWithLocation.getOrNull(index)?.type) {
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

        fun findSteps(index: Int): Steps? {
            return when (listOfInstructionsWithLocation.getOrNull(index)?.type) {
                InstructionCardName.NUMERO_2 -> return Steps.STEPS_2
                InstructionCardName.NUMERO_3 -> return Steps.STEPS_3
                InstructionCardName.NUMERO_4 -> return Steps.STEPS_4
                InstructionCardName.NUMERO_5 -> return Steps.STEPS_5
                InstructionCardName.NUMERO_6 -> return Steps.STEPS_6
                InstructionCardName.NUMERO_AL_AZAR -> return Steps.RANDOM
                else -> null
            }
        }

        fun findRotationAngle(index: Int): RotationAngle? {
            return when (listOfInstructionsWithLocation.getOrNull(index)?.type) {
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

        val listOfInstructions = mutableListOf<InstructionContract>()
        for (block in mlKitText.textBlocks) {
            for (line in block.lines) {
                val instructionType = instructionTypeRecognizer.getInstructionType(line.text)
                instructionType?.let {
                    listOfInstructionsWithLocation.add(
                        InstructionWithLocation(
                            it,
                            line.cornerPoints?.get(0)?.x ?: 0,
                            line.cornerPoints?.get(0)?.y ?: 0
                        )
                    )
                }
            }
        }

        listOfInstructionsWithLocation
            .sortedBy { it.x }
            .forEachIndexed { index, instruction ->
                when (instruction.type) {
                    InstructionCardName.AVANZAR -> listOfInstructions.add(
                        MoveForward(
                            findSteps(index - 1) ?: findSteps(index + 1) ?: Steps.RANDOM
                        )
                    )
                    InstructionCardName.BAJAR_LAPIZ -> listOfInstructions.add(PencilDown())
                    InstructionCardName.FUNCION -> listOfInstructions.add(FunctionExecute())
                    InstructionCardName.FUNCION_COMIENZO -> listOfInstructions.add(FunctionStart())
                    InstructionCardName.FUNCION_FIN -> listOfInstructions.add(FunctionEnd())
                    InstructionCardName.GIRAR_DERECHA -> listOfInstructions.add(
                        RotateRight(
                            findRotationAngle(index - 1) ?: findRotationAngle(index + 1) ?: RotationAngle.RANDOM
                        )
                    )
                    InstructionCardName.GIRAR_IZQUIERDA -> listOfInstructions.add(
                        RotateLeft(
                            findRotationAngle(index - 1) ?: findRotationAngle(index + 1) ?: RotationAngle.RANDOM
                        )
                    )
                    InstructionCardName.LEDS -> listOfInstructions.add(
                        Led(
                            findLedColor(index - 1) ?: findLedColor(index + 1) ?: LedColor.RANDOM
                        )
                    )
                    InstructionCardName.LEVANTAR_LAPIZ -> listOfInstructions.add(PencilUp())

                    InstructionCardName.PROGRAMA_COMIENZO -> listOfInstructions.add(ProgramStart())
                    InstructionCardName.PROGRAMA_FIN -> listOfInstructions.add(ProgramEnd())
                    InstructionCardName.REPETIR_COMIENZO -> listOfInstructions.add(
                        RepeatStart(
                            findSteps(index - 1) ?: findSteps(index + 1) ?: Steps.RANDOM
                        )
                    )
                    InstructionCardName.REPETIR_FIN -> listOfInstructions.add(RepeatEnd())
                    InstructionCardName.RETROCEDER -> listOfInstructions.add(
                        MoveBackward(
                            findSteps(index - 1) ?: findSteps(index + 1) ?: Steps.RANDOM
                        )
                    )
                    InstructionCardName.TOCAR_CORCHEA -> listOfInstructions.add(
                        Eighth(
                            findNote(index - 1) ?: findNote(index + 1) ?: MusicNote.RANDOM
                        )
                    )
                    InstructionCardName.TOCAR_MELODIA -> listOfInstructions.add(
                        Melody(
                            findNote(index - 1) ?: findNote(index + 1) ?: MusicNote.RANDOM
                        )
                    )
                    InstructionCardName.TOCAR_NEGRA -> listOfInstructions.add(
                        Quarter(
                            findNote(index - 1) ?: findNote(index + 1) ?: MusicNote.RANDOM
                        )
                    )
                    else -> { // Do Nothing
                    }
                }
            }
        return listOfInstructions
    }

}

data class InstructionWithLocation(
    val type: InstructionCardName,
    val x: Int, val y: Int
)