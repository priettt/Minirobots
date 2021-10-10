package com.example.minirobots.instructionsList.domain.entities

import androidx.annotation.DrawableRes
import com.example.minirobots.R

private const val STEP_DISTANCE = 30

interface Modifier {
    val text: String

    @get:DrawableRes
    val imageDrawable: Int
}

sealed interface UIInstruction {
    val name: String
    var modifier: Modifier?

    @get:DrawableRes
    val imageDrawable: Int

}

data class RotateRight(
    override val name: String = "Rotar a la derecha",
    override val imageDrawable: Int = R.drawable.girar_derecha,
    override var modifier: Modifier? = RotationAngle.RANDOM
) : UIInstruction

data class RotateLeft(
    override val name: String = "Rotar a la izquierda",
    override val imageDrawable: Int = R.drawable.girar_izquierda,
    override var modifier: Modifier? = RotationAngle.RANDOM
) : UIInstruction

data class Led(
    override val name: String = "Leds",
    override val imageDrawable: Int = R.drawable.leds,
    override var modifier: Modifier? = LedColor.RANDOM
) : UIInstruction

data class MoveForward(
    override val name: String = "Avanzar",
    override val imageDrawable: Int = R.drawable.adelante,
    override var modifier: Modifier? = Steps.RANDOM
) : UIInstruction

data class MoveBackward(
    override val name: String = "Retroceder",
    override val imageDrawable: Int = R.drawable.atras,
    override var modifier: Modifier? = Steps.RANDOM
) : UIInstruction

data class RepeatStart(
    override val name: String = "Inicio de repetición",
    override val imageDrawable: Int = R.drawable.repetir_comienzo,
    override var modifier: Modifier? = Steps.RANDOM
) : UIInstruction

data class Quarter(
    override val name: String = "Reproducir negra",
    override val imageDrawable: Int = R.drawable.negra,
    override var modifier: Modifier? = MusicNote.RANDOM
) : UIInstruction

data class Eighth(
    override val name: String = "Reproducir corchea",
    override val imageDrawable: Int = R.drawable.corchea,
    override var modifier: Modifier? = MusicNote.RANDOM
) : UIInstruction

data class Melody(
    override val name: String = "Reproducir melodia",
    override val imageDrawable: Int = R.drawable.melodia,
    override var modifier: Modifier? = MusicNote.RANDOM
) : UIInstruction

data class ProgramStart(
    override val name: String = "Inicio Programa",
    override val imageDrawable: Int = R.drawable.programa_comienzo,
    override var modifier: Modifier? = null
) : UIInstruction

data class ProgramEnd(
    override val name: String = "Fin Programa",
    override val imageDrawable: Int = R.drawable.programa_fin,
    override var modifier: Modifier? = null
) : UIInstruction

data class RepeatEnd(
    override val name: String = "Fin Repeticion",
    override val imageDrawable: Int = R.drawable.repetir_fin,
    override var modifier: Modifier? = null
) : UIInstruction

data class FunctionStart(
    override val name: String = "Inicio Función",
    override val imageDrawable: Int = R.drawable.inicio_funcion,
    override var modifier: Modifier? = null
) : UIInstruction

data class FunctionEnd(
    override val name: String = "Fin Función",
    override val imageDrawable: Int = R.drawable.fin_funcion,
    override var modifier: Modifier? = null
) : UIInstruction

data class FunctionExecute(
    override val name: String = "Ejecutar Función",
    override val imageDrawable: Int = R.drawable.funcion,
    override var modifier: Modifier? = null
) : UIInstruction

data class PencilDown(
    override val name: String = "Lápiz Abajo",
    override val imageDrawable: Int = R.drawable.lapiz_abajo,
    override var modifier: Modifier? = null
) : UIInstruction

data class PencilUp(
    override val name: String = "Lápiz Arriba",
    override val imageDrawable: Int = R.drawable.lapiz_arriba,
    override var modifier: Modifier? = null
) : UIInstruction

enum class MusicNote : Modifier {
    A {
        override val text: String
            get() = "Nota A"

        override val imageDrawable: Int
            get() = R.drawable.nota_a
    },
    B {
        override val text: String
            get() = "Nota B"

        override val imageDrawable: Int
            get() = R.drawable.nota_b
    },
    C {
        override val text: String
            get() = "Nota C"

        override val imageDrawable: Int
            get() = R.drawable.nota_c
    },
    D {
        override val text: String
            get() = "Nota D"

        override val imageDrawable: Int
            get() = R.drawable.nota_d
    },
    E {
        override val text: String
            get() = "Nota E"

        override val imageDrawable: Int
            get() = R.drawable.nota_e
    },
    F {
        override val text: String
            get() = "Nota F"

        override val imageDrawable: Int
            get() = R.drawable.nota_f
    },
    G {
        override val text: String
            get() = "Nota G"

        override val imageDrawable: Int
            get() = R.drawable.nota_g
    },
    SILENCE {
        override val text: String
            get() = "Silenciar"

        override val imageDrawable: Int
            get() = R.drawable.silencio
    },
    RANDOM {
        override val text: String
            get() = "Nota Aleatoria"

        override val imageDrawable: Int
            get() = R.drawable.nota_al_azar
    }
}

enum class LedColor : Modifier {
    RED {
        override val text: String
            get() = "Color Rojo"

        override val imageDrawable: Int
            get() = R.drawable.color_rojo
    },
    BLUE {
        override val text: String
            get() = "Color Azul"

        override val imageDrawable: Int
            get() = R.drawable.color_azul
    },
    GREEN {
        override val text: String
            get() = "Color Verde"

        override val imageDrawable: Int
            get() = R.drawable.color_verde
    },
    PINK {
        override val text: String
            get() = "Color Rosa"

        override val imageDrawable: Int
            get() = R.drawable.color_rosa
    },
    YELLOW {
        override val text: String
            get() = "Color Amarillo"

        override val imageDrawable: Int
            get() = R.drawable.color_amarillo
    },
    LIGHT_BLUE {
        override val text: String
            get() = "Color Celeste"

        override val imageDrawable: Int
            get() = R.drawable.color_celeste
    },
    WHITE {
        override val text: String
            get() = "Color Blanco"

        override val imageDrawable: Int
            get() = R.drawable.color_al_azar
    },
    RANDOM {
        override val text: String
            get() = "Color Aleatorio"

        override val imageDrawable: Int
            get() = R.drawable.color_al_azar
    },
    NONE {
        override val text: String
            get() = "Apagar Led"

        override val imageDrawable: Int
            get() = R.drawable.color_al_azar
    }
}

enum class RotationAngle : Modifier {
    ANGLE_30 {
        override val text: String
            get() = "30 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_30
    },
    ANGLE_36 {
        override val text: String
            get() = "36 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_36
    },
    ANGLE_45 {
        override val text: String
            get() = "45 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_45
    },
    ANGLE_60 {
        override val text: String
            get() = "60 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_60
    },
    ANGLE_72 {
        override val text: String
            get() = "72 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_72
    },
    ANGLE_108 {
        override val text: String
            get() = "108 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_108
    },
    ANGLE_120 {
        override val text: String
            get() = "120 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_120
    },
    ANGLE_135 {
        override val text: String
            get() = "135 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_135
    },
    ANGLE_144 {
        override val text: String
            get() = "144 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_144
    },
    ANGLE_150 {
        override val text: String
            get() = "150 Grados"

        override val imageDrawable: Int
            get() = R.drawable.angulo_150
    },
    RANDOM {
        override val text: String
            get() = "Angulo Aleatorio"

        override val imageDrawable: Int
            get() = R.drawable.angulo_al_azar
    },
}

enum class Steps : Modifier {
    STEPS_2 {
        override val text: String
            get() = "2 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.numero_2
    },
    STEPS_3 {
        override val text: String
            get() = "3 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.numero_3
    },
    STEPS_4 {
        override val text: String
            get() = "4 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.numero_4
    },
    STEPS_5 {
        override val text: String
            get() = "5 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.numero_5
    },
    STEPS_6 {
        override val text: String
            get() = "6 Pasos"

        override val imageDrawable: Int
            get() = R.drawable.numero_6
    },
    RANDOM {
        override val text: String
            get() = "Pasos Aleatorios"

        override val imageDrawable: Int
            get() = R.drawable.numero_al_azar
    },
}