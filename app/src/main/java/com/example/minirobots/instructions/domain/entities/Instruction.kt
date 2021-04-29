package com.example.minirobots.instructions.domain.entities

import androidx.annotation.DrawableRes
import com.example.minirobots.R

interface Modifier {
    val text: String

    @get:DrawableRes
    val imageDrawable: Int
}

interface Instruction {
    val name: String
    val modifier: Modifier?

    @get:DrawableRes
    val imageDrawable: Int
}

data class RotateRight(
    override val name: String = "Rotar a la derecha",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier = RotationAngle.RANDOM,
) : Instruction

data class RotateLeft(
    override val name: String = "Rotar a la izquierda",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier = RotationAngle.RANDOM,
) : Instruction

data class Led(
    override val name: String = "Leds",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier = RotationAngle.RANDOM,
) : Instruction

data class MoveForward(
    override val name: String = "Avanzar",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier = Steps.RANDOM,
) : Instruction

data class MoveBackward(
    override val name: String = "Retroceder",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier = Steps.RANDOM,
) : Instruction

data class RepeatStart(
    override val name: String = "Inicio de repetición",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier = Steps.RANDOM,
) : Instruction

data class Quarter(
    override val name: String = "Reproducir negra",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier = MusicNote.RANDOM,
) : Instruction

data class Eighth(
    override val name: String = "Reproducir corchea",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier = MusicNote.RANDOM,
) : Instruction

data class Melody(
    override val name: String = "Reproducir melodia",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier = MusicNote.RANDOM,
) : Instruction

data class ProgramStart(
    override val name: String = "Inicio Programa",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier? = null
) : Instruction

data class ProgramEnd(
    override val name: String = "Fin Programa",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier? = null
) : Instruction

data class RepeatEnd(
    override val name: String = "Fin Repeticion",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier? = null
) : Instruction

data class FunctionStart(
    override val name: String = "Inicio Función",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier? = null
) : Instruction

data class FunctionEnd(
    override val name: String = "Fin Función",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier? = null
) : Instruction

data class FunctionExecute(
    override val name: String = "Ejecutar Función",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier? = null
) : Instruction

data class PencilDown(
    override val name: String = "Lápiz Abajo",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier? = null
) : Instruction

data class PencilUp(
    override val name: String = "Lápiz Arriba",
    override val imageDrawable: Int = R.drawable.inicio_programa,
    override val modifier: Modifier? = null
) : Instruction

enum class MusicNote : Modifier {
    A {
        override val text: String
            get() = "Nota A"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    B {
        override val text: String
            get() = "Nota B"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    C {
        override val text: String
            get() = "Nota C"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    D {
        override val text: String
            get() = "Nota D"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    E {
        override val text: String
            get() = "Nota E"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    F {
        override val text: String
            get() = "Nota F"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    G {
        override val text: String
            get() = "Nota G"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    SILENCE {
        override val text: String
            get() = "Silenciar"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    RANDOM {
        override val text: String
            get() = "Nota Aleatoria"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    }
}

enum class LedColor : Modifier {
    RED {
        override val text: String
            get() = "Color Rojo"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    BLUE {
        override val text: String
            get() = "Color Azul"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    GREEN {
        override val text: String
            get() = "Color Verde"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    PINK {
        override val text: String
            get() = "Color Rosa"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    YELLOW {
        override val text: String
            get() = "Color Amarillo"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    LIGHT_BLUE {
        override val text: String
            get() = "Color Celeste"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    WHITE {
        override val text: String
            get() = "Color Blanco"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    RANDOM {
        override val text: String
            get() = "Color Aleatorio"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    NONE {
        override val text: String
            get() = "Apagar Led"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    }
}

enum class RotationAngle : Modifier {
    ANGLE_30 {
        override val text: String
            get() = "30 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    ANGLE_36 {
        override val text: String
            get() = "36 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    ANGLE_45 {
        override val text: String
            get() = "45 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    ANGLE_60 {
        override val text: String
            get() = "60 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    ANGLE_72 {
        override val text: String
            get() = "72 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    ANGLE_108 {
        override val text: String
            get() = "108 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    ANGLE_120 {
        override val text: String
            get() = "120 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    ANGLE_135 {
        override val text: String
            get() = "135 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    ANGLE_144 {
        override val text: String
            get() = "144 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    ANGLE_150 {
        override val text: String
            get() = "150 Grados"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    RANDOM {
        override val text: String
            get() = "Angulo Aleatorio"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
}

enum class Steps : Modifier {
    STEPS_2 {
        override val text: String
            get() = "2 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    STEPS_3 {
        override val text: String
            get() = "3 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    STEPS_4 {
        override val text: String
            get() = "4 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    STEPS_5 {
        override val text: String
            get() = "5 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    STEPS_6 {
        override val text: String
            get() = "6 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
    RANDOM {
        override val text: String
            get() = "Pasos Aleatorios"

        override val imageDrawable: Int
            get() = R.drawable.inicio_programa
    },
}