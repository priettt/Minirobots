package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.takePicture.domain.entities.PieceName
import com.example.minirobots.instructionsList.domain.entities.*
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class MLKitTextMapper @Inject constructor(
    private val getRecognizedPieceNames: GetRecognizedPieceNames,
    private val generateInstructions: GenerateInstructions
) {
    fun map(mlKitText: Text): List<UIInstruction> {
        val instructionCardNames = getRecognizedPieceNames(mlKitText)
        return generateInstructions(instructionCardNames)
    }
}

class GenerateInstructions @Inject constructor() {
    operator fun invoke(pieceNames: List<PieceName>): List<UIInstruction> {
        val instructions = mutableListOf<UIInstruction>()
        pieceNames.forEachIndexed { index, instruction ->
            when (instruction) {
                PieceName.AVANZAR -> instructions.add(MoveForward(modifier = getSteps(pieceNames, index)))
                PieceName.BAJAR_LAPIZ -> instructions.add(PencilDown())
                PieceName.FUNCION -> instructions.add(FunctionExecute())
                PieceName.FUNCION_COMIENZO -> instructions.add(FunctionStart())
                PieceName.FUNCION_FIN -> instructions.add(FunctionEnd())
                PieceName.GIRAR_DERECHA -> instructions.add(RotateRight(modifier = getAngle(pieceNames, index)))
                PieceName.GIRAR_IZQUIERDA -> instructions.add(RotateLeft(modifier = getAngle(pieceNames, index)))
                PieceName.LEDS -> instructions.add(Led(modifier = getLedColor(pieceNames, index)))
                PieceName.LEVANTAR_LAPIZ -> instructions.add(PencilUp())
                PieceName.PROGRAMA_COMIENZO -> instructions.add(ProgramStart())
                PieceName.PROGRAMA_FIN -> instructions.add(ProgramEnd())
                PieceName.REPETIR_COMIENZO -> instructions.add(RepeatStart(modifier = getSteps(pieceNames, index)))
                PieceName.REPETIR_FIN -> instructions.add(RepeatEnd())
                PieceName.RETROCEDER -> instructions.add(MoveBackward(modifier = getSteps(pieceNames, index)))
                PieceName.TOCAR_CORCHEA -> instructions.add(Eighth(modifier = getMusicNote(pieceNames, index)))
                PieceName.TOCAR_MELODIA -> instructions.add(Melody(modifier = getMusicNote(pieceNames, index)))
                PieceName.TOCAR_NEGRA -> instructions.add(Quarter(modifier = getMusicNote(pieceNames, index)))
                else -> { // Do Nothing
                }
            }
        }
        return instructions
    }

    private fun getSteps(pieceNames: List<PieceName>, index: Int): Steps {

        fun findSteps(index: Int): Steps? {
            return when (pieceNames.getOrNull(index)) {
                PieceName.NUMERO_2 -> return Steps.STEPS_2
                PieceName.NUMERO_3 -> return Steps.STEPS_3
                PieceName.NUMERO_4 -> return Steps.STEPS_4
                PieceName.NUMERO_5 -> return Steps.STEPS_5
                PieceName.NUMERO_6 -> return Steps.STEPS_6
                PieceName.NUMERO_AL_AZAR -> return Steps.RANDOM
                else -> null
            }
        }

        return findSteps(index - 1) ?: findSteps(index + 1) ?: Steps.RANDOM
    }

    private fun getLedColor(pieceNames: List<PieceName>, index: Int): LedColor {

        fun findLedColor(index: Int): LedColor? {
            return when (pieceNames.getOrNull(index)) {
                PieceName.COLOR_AMARILLO -> LedColor.YELLOW
                PieceName.COLOR_AZUL -> LedColor.BLUE
                PieceName.COLOR_BLANCO -> LedColor.WHITE
                PieceName.COLOR_CIAN -> LedColor.LIGHT_BLUE
                PieceName.COLOR_MAGENTA -> LedColor.PINK
                PieceName.COLOR_ROJO -> LedColor.RED
                PieceName.COLOR_VERDE -> LedColor.GREEN
                PieceName.COLOR_AL_AZAR -> LedColor.RANDOM
                PieceName.NO_COLOR -> LedColor.NONE
                else -> null
            }
        }

        return findLedColor(index - 1) ?: findLedColor(index + 1) ?: LedColor.RANDOM
    }

    private fun getMusicNote(pieceNames: List<PieceName>, index: Int): MusicNote {

        fun findNote(index: Int): MusicNote? {
            return when (pieceNames.getOrNull(index)) {
                PieceName.NOTA_A -> MusicNote.A
                PieceName.NOTA_B -> MusicNote.B
                PieceName.NOTA_C -> MusicNote.C
                PieceName.NOTA_D -> MusicNote.D
                PieceName.NOTA_E -> MusicNote.E
                PieceName.NOTA_F -> MusicNote.F
                PieceName.NOTA_G -> MusicNote.G
                PieceName.NOTA_AL_AZAR -> MusicNote.RANDOM
                PieceName.NO_SONIDO -> MusicNote.SILENCE
                else -> null
            }
        }

        return findNote(index - 1) ?: findNote(index + 1) ?: MusicNote.RANDOM
    }

    private fun getAngle(pieceNames: List<PieceName>, index: Int): RotationAngle {

        fun findRotationAngle(index: Int): RotationAngle? {
            return when (pieceNames.getOrNull(index)) {
                PieceName.ANGULO_30 -> RotationAngle.ANGLE_30
                PieceName.ANGULO_36 -> RotationAngle.ANGLE_36
                PieceName.ANGULO_45 -> RotationAngle.ANGLE_45
                PieceName.ANGULO_60 -> RotationAngle.ANGLE_60
                PieceName.ANGULO_72 -> RotationAngle.ANGLE_72
                PieceName.ANGULO_108 -> RotationAngle.ANGLE_108
                PieceName.ANGULO_120 -> RotationAngle.ANGLE_120
                PieceName.ANGULO_135 -> RotationAngle.ANGLE_135
                PieceName.ANGULO_144 -> RotationAngle.ANGLE_144
                PieceName.ANGULO_150 -> RotationAngle.ANGLE_150
                PieceName.ANGULO_AL_AZAR -> RotationAngle.RANDOM
                else -> null
            }
        }
        return findRotationAngle(index - 1) ?: findRotationAngle(index + 1) ?: RotationAngle.RANDOM
    }

}

data class LocalizedPieceName(
    val type: PieceName,
    val x: Int, val y: Int
)