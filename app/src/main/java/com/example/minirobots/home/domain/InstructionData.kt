package com.example.minirobots.home.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class InstructionCardName(val text: String) : Parcelable {
    ANGULO_30("ANGULO 30"),
    ANGULO_36("ANGULO 36"),
    ANGULO_45("ANGULO 45"),
    ANGULO_60("ANGULO 60"),
    ANGULO_72("ANGULO 72"),
    ANGULO_108("ANGULO 108"),
    ANGULO_120("ANGULO 120"),
    ANGULO_135("ANGULO 135"),
    ANGULO_144("ANGULO 144"),
    ANGULO_150("ANGULO 150"),
    ANGULO_AL_AZAR("ANGULO AL AZAR"),
    AVANZAR("AVANZAR"),
    BAJAR_LAPIZ("BAJAR LAPIZ"),
    COLOR_AL_AZAR("COLOR AL AZAR"),
    COLOR_AMARILLO("COLOR AMARILLO"),
    COLOR_AZUL("COLOR AZUL"),
    COLOR_BLANCO("COLOR BLANCO"),
    COLOR_CIAN("COLOR CIAN"),
    COLOR_MAGENTA("COLOR MAGENTA"),
    COLOR_ROJO("COLOR ROJO"),
    COLOR_VERDE("COLOR VERDE"),
    FUNCION("FUNCION"),
    FUNCION_COMIENZO("FUNCION COMIENZO"),
    FUNCION_FIN("FUNCION FIN"),
    GIRAR_DERECHA("GIRAR DERECHA"),
    GIRAR_IZQUIERDA("GIRAR IZQUIERDA"),
    LEDS("LEDS"),
    LEVANTAR_LAPIZ("LEVANTAR LAPIZ"),
    NO_COLOR("NO COLOR"),
    NO_SONIDO("NO SONIDO"),
    NOTA_A("NOTA A"),
    NOTA_AL_AZAR("NOTA AL AZAR"),
    NOTA_B("NOTA B"),
    NOTA_C("NOTA C"),
    NOTA_D("NOTA D"),
    NOTA_E("NOTA E"),
    NOTA_F("NOTA F"),
    NOTA_G("NOTA G"),
    NUMERO_2("NUMERO 2"),
    NUMERO_3("NUMERO 3"),
    NUMERO_4("NUMERO 4"),
    NUMERO_5("NUMERO 5"),
    NUMERO_6("NUMERO 6"),
    NUMERO_AL_AZAR("NUMERO AL AZAR"),
    PROGRAMA_COMIENZO("PROGRAMA COMIENZO"),
    PROGRAMA_FIN("PROGRAMA FIN"),
    REPETIR_COMIENZO("REPETIR COMIENZO"),
    REPETIR_FIN("REPETIR FIN"),
    RETROCEDER("RETROCEDER"),
    TOCAR_CORCHEA("TOCAR CORCHEA"),
    TOCAR_MELODIA("TOCAR MELODIA"),
    TOCAR_NEGRA("TOCAR NEGRA")
}

interface Instruction {
    val name: String
    val imageLocation: String
}

data class RotateRight(
    var angle: RotationAngle,
    override val name: String = "Derecha",
    override val imageLocation: String = "tarjeta_derecha",
) : Instruction

data class RotateLeft(
    var angle: RotationAngle,
    override val name: String = "Izquierda",
    override val imageLocation: String = "tarjeta_izquierda",
) : Instruction

data class Led(
    var color: LedColor,
    override val name: String = "Leds",
    override val imageLocation: String = "tarjeta_leds",
) : Instruction

data class MoveForward(
    var steps: Steps,
    override val name: String = "Adelante",
    override val imageLocation: String = "tarjeta_adelante",
) : Instruction

data class MoveBackward(
    var steps: Steps,
    override val name: String = "Atras",
    override val imageLocation: String = "tarjeta_atras",
) : Instruction

data class RepeatStart(
    var steps: Steps,
    override val name: String = "Repetir",
    override val imageLocation: String = "tarjeta_repetir",
) : Instruction

data class Quarter(
    var note: MusicNote,
    override val name: String = "Negra",
    override val imageLocation: String = "tarjeta_negra",
) : Instruction

data class Eighth(
    var note: MusicNote,
    override val name: String = "Corchea",
    override val imageLocation: String = "tarjeta_corchea",
) : Instruction

data class Melody(
    var note: MusicNote,
    override val name: String = "Melodia",
    override val imageLocation: String = "tarjeta_melodia",
) : Instruction

data class ProgramStart(
    override val name: String = "Inicio Programa",
    override val imageLocation: String = "tarjeta_inicio_programa",
) : Instruction

data class ProgramEnd(
    override val name: String = "Fin Programa",
    override val imageLocation: String = "tarjeta_fin_programa",
) : Instruction


data class RepeatEnd(
    override val name: String = "Fin Repeticion",
    override val imageLocation: String = "tarjeta_fin_repeticion",
) : Instruction

data class FunctionStart(
    override val name: String = "Inicio Función",
    override val imageLocation: String = "tarjeta_inicio_funcion",
) : Instruction

data class FunctionEnd(
    override val name: String = "Fin Función",
    override val imageLocation: String = "tarjeta_fin_funcion",
) : Instruction

data class FunctionExecute(
    override val name: String = "Ejecutar Función",
    override val imageLocation: String = "tarjeta_funcion",
) : Instruction

data class PencilDown(
    override val name: String = "Lápiz Abajo",
    override val imageLocation: String = "tarjeta_lapiz_abajo",
) : Instruction

data class PencilUp(
    override val name: String = "Lápiz Arriba",
    override val imageLocation: String = "tarjeta_lapiz_arriba",
) : Instruction

enum class MusicInstructionType {
    QUARTER,
    EIGHTH,
    MELODY
}

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
