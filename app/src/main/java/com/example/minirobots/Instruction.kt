package com.example.minirobots

/*
    Not sure if this is the best approach, because it involves a lot of mapping.
    It is cleaner to store, as we only have actions and modifiers, and then we
    adapt this to the UI to show, and to the parser to send it to the API.

    An alternative could be having absolutely everything regarding instructions in the same class,
    displayName, recognitionName, resourceId, modifiers.
 */


data class Instruction(val action: Action, val modifier: Modifier?)

enum class Action {
    AVANZAR,
    BAJAR_LAPIZ,
    FUNCION,
    FUNCION_COMIENZO,
    FUNCION_FIN,
    GIRAR_DERECHA,
    GIRAR_IZQUIERDA,
    LEDS,
    LEVANTAR_LAPIZ,
    PROGRAMA_COMIENZO,
    PROGRAMA_FIN,
    REPETIR_COMIENZO,
    REPETIR_FIN,
    RETROCEDER,
    TOCAR_CORCHEA,
    TOCAR_MELODIA,
    TOCAR_NEGRA,
}

enum class Modifier {
    ANGULO_30,
    ANGULO_36,
    ANGULO_45,
    ANGULO_60,
    ANGULO_72,
    ANGULO_108,
    ANGULO_120,
    ANGULO_135,
    ANGULO_144,
    ANGULO_150,
    ANGULO_AL_AZAR,
    COLOR_AL_AZAR,
    COLOR_AMARILLO,
    COLOR_AZUL,
    COLOR_BLANCO,
    COLOR_CIAN,
    COLOR_MAGENTA,
    COLOR_ROJO,
    COLOR_VERDE,
    NO_COLOR,
    NO_SONIDO,
    NOTA_A,
    NOTA_AL_AZAR,
    NOTA_B,
    NOTA_C,
    NOTA_D,
    NOTA_E,
    NOTA_F,
    NOTA_G,
    NUMERO_2,
    NUMERO_3,
    NUMERO_4,
    NUMERO_5,
    NUMERO_6,
    NUMERO_AL_AZAR,
}
