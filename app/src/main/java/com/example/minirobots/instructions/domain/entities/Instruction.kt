package com.example.minirobots.instructions.domain.entities

import androidx.annotation.DrawableRes
import com.example.minirobots.R

interface Instruction {
    val name: String

    @get:DrawableRes
    val imageDrawable: Int
}

data class RotateRight(
    var angle: RotationAngle = RotationAngle.RANDOM,
    override val name: String = "Derecha",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class RotateLeft(
    var angle: RotationAngle = RotationAngle.RANDOM,
    override val name: String = "Izquierda",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class Led(
    var color: LedColor = LedColor.RANDOM,
    override val name: String = "Leds",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class MoveForward(
    var steps: Steps = Steps.RANDOM,
    override val name: String = "Adelante",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class MoveBackward(
    var steps: Steps = Steps.RANDOM,
    override val name: String = "Atras",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class RepeatStart(
    var steps: Steps = Steps.RANDOM,
    override val name: String = "Repetir",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class Quarter(
    var note: MusicNote = MusicNote.RANDOM,
    override val name: String = "Negra",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class Eighth(
    var note: MusicNote = MusicNote.RANDOM,
    override val name: String = "Corchea",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class Melody(
    var note: MusicNote = MusicNote.RANDOM,
    override val name: String = "Melodia",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class ProgramStart(
    override val name: String = "Inicio Programa",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class ProgramEnd(
    override val name: String = "Fin Programa",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class RepeatEnd(
    override val name: String = "Fin Repeticion",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class FunctionStart(
    override val name: String = "Inicio Función",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class FunctionEnd(
    override val name: String = "Fin Función",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class FunctionExecute(
    override val name: String = "Ejecutar Función",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class PencilDown(
    override val name: String = "Lápiz Abajo",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

data class PencilUp(
    override val name: String = "Lápiz Arriba",
    override val imageDrawable: Int = R.drawable.inicio_programa,
) : Instruction

enum class MusicNote {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    SILENCE,
    RANDOM
}

enum class LedColor {
    RED,
    BLUE,
    GREEN,
    PINK,
    YELLOW,
    LIGHT_BLUE,
    WHITE,
    RANDOM,
    NONE
}

enum class RotationAngle {
    ANGLE_30,
    ANGLE_36,
    ANGLE_45,
    ANGLE_60,
    ANGLE_72,
    ANGLE_108,
    ANGLE_120,
    ANGLE_135,
    ANGLE_144,
    ANGLE_150,
    RANDOM,
}

enum class Steps {
    STEPS_2,
    STEPS_3,
    STEPS_4,
    STEPS_5,
    STEPS_6,
    RANDOM,
}
