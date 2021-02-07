package com.example.minirobots.home.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Instruction(val type: InstructionType) : Parcelable

@Parcelize
enum class InstructionType(val text: String) : Parcelable {
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

interface InstructionInterface {
    fun getJson(): String
}

data class MovementInstruction(val direction: MovementDirection, val steps: Int) : InstructionInterface {
    override fun getJson(): String {
        TODO("Not yet implemented")
    }
}

enum class MovementDirection {
    FORWARD,
    BACKWARD
}

data class LoopInstruction(val steps: Int) : InstructionInterface {
    override fun getJson(): String {
        TODO("Not yet implemented")
    }
}

data class RotationInstruction(val direction: RotationDirection, val angle: Int) : InstructionInterface {
    override fun getJson(): String {
        TODO("Not yet implemented")
    }
}

enum class RotationDirection {
    LEFT,
    RIGHT
}

data class StartInstruction(val instructionType: StartInstructionType) : InstructionInterface {
    override fun getJson(): String {
        TODO("Not yet implemented")
    }
}

enum class StartInstructionType {
    PROGRAM,
    FUNCTION
}

data class PencilInstruction(val stopWriting: Boolean) : InstructionInterface {
    override fun getJson(): String {
        TODO("Not yet implemented")
    }
}

class FunctionInstruction : InstructionInterface {
    override fun getJson(): String {
        TODO("Not yet implemented")
    }
}

data class MusicInstruction(val type: MusicInstructionType, val note: MusicInstructionNote) : InstructionInterface {
    override fun getJson(): String {
        TODO("Not yet implemented")
    }
}

enum class MusicInstructionType {
    QUARTER,
    EIGHTH,
    MELODY
}

enum class MusicInstructionNote {
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

data class LedsInstruction(val color: LedsColor) : InstructionInterface {
    override fun getJson(): String {
        TODO("Not yet implemented")
    }
}

enum class LedsColor {
    RED,
    BLUE,
    GREEN,
    PINK,
    RANDOM,
    YELLOW,
    LIGHT_BLUE,
}
