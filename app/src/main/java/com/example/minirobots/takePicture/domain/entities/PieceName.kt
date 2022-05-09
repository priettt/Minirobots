package com.example.minirobots.takePicture.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
    All of the possible piece names, both actions and modifiers.
    Each name is associated with the text we are using to recognize said instruction.
*/

@Parcelize
enum class PieceName(val text: String) : Parcelable {
    ANGULO_30("30°"),
    ANGULO_36("36°"),
    ANGULO_45("45°"),
    ANGULO_60("60°"),
    ANGULO_72("72°"),
    ANGULO_108("108°"),
    ANGULO_120("120°"),
    ANGULO_135("135°"),
    ANGULO_144("144°"),
    ANGULO_150("150°"),
    ANGULO_AL_AZAR("ANGULO"),
    AVANZAR("ADELANTE"),
    BAJAR_LAPIZ("ABAJO"),
    COLOR_AL_AZAR("COLOR"),
    COLOR_AMARILLO("AMARILLO"),
    COLOR_AZUL("AZUL"),
    COLOR_CELESTE("CELESTE"),
    COLOR_ROSA("ROSA"),
    COLOR_ROJO("ROJO"),
    COLOR_VERDE("VERDE"),
    FUNCION("FUNCION"),
    FUNCION_COMIENZO("INICIO FN"),
    FUNCION_FIN("FIN FN"),
    GIRAR_DERECHA("DERECHA"),
    GIRAR_IZQUIERDA("IZQUIERDA"),
    LEDS("LEDS"),
    LEVANTAR_LAPIZ("ARRIBA"),
    NO_COLOR("APAGADO"),
    NO_SONIDO("SILENCIO"),
    NOTA_A("A LA"),
    NOTA_AL_AZAR("NOTA"),
    NOTA_B("SI"),
    NOTA_C("DO"),
    NOTA_D("RE"),
    NOTA_E("MI"),
    NOTA_F("FA"),
    NOTA_G("SOL"),
    NUMERO_2("2 DOS"),
    NUMERO_3("3 TRES"),
    NUMERO_4("4 CUATRO"),
    NUMERO_5("5 CINCO"),
    NUMERO_6("6 SEIS"),
    NUMERO_AL_AZAR("NUMERO"),
    PROGRAMA_COMIENZO("INICIO"),
    PROGRAMA_FIN("FIN"),
    REPETIR_COMIENZO("REPETIR"),
    REPETIR_FIN("FIN REPETIR"),
    RETROCEDER("ATRAS"),
    TOCAR_CORCHEA("CORCHEA"),
    TOCAR_MELODIA("MELODIA"),
    TOCAR_NEGRA("NEGRA")
}
