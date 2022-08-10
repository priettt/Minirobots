package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.common.domain.Action
import com.example.minirobots.common.domain.Modifier
import javax.inject.Inject

class GetAvailableModifiers @Inject constructor() {
    private val numberModifiers = listOf(
        Modifier.NUMERO_2,
        Modifier.NUMERO_3,
        Modifier.NUMERO_4,
        Modifier.NUMERO_5,
        Modifier.NUMERO_6,
        Modifier.NUMERO_AL_AZAR
    )

    private val angleModifiers = listOf(
        Modifier.ANGULO_30,
        Modifier.ANGULO_36,
        Modifier.ANGULO_45,
        Modifier.ANGULO_60,
        Modifier.ANGULO_72,
        Modifier.ANGULO_108,
        Modifier.ANGULO_120,
        Modifier.ANGULO_135,
        Modifier.ANGULO_144,
        Modifier.ANGULO_150,
        Modifier.ANGULO_AL_AZAR,
    )

    private val colorModifiers = listOf(
        Modifier.COLOR_AMARILLO,
        Modifier.COLOR_AZUL,
        Modifier.COLOR_CELESTE,
        Modifier.COLOR_ROSA,
        Modifier.COLOR_ROJO,
        Modifier.COLOR_VERDE,
        Modifier.COLOR_AL_AZAR,
        Modifier.NO_COLOR,
    )

    private val musicModifiers = listOf(
        Modifier.NOTA_A,
        Modifier.NOTA_B,
        Modifier.NOTA_C,
        Modifier.NOTA_D,
        Modifier.NOTA_E,
        Modifier.NOTA_F,
        Modifier.NOTA_G,
        Modifier.NO_SONIDO,
        Modifier.NOTA_AL_AZAR,
    )

    operator fun invoke(action: Action?): List<Modifier> {
        return when (action) {
            Action.AVANZAR, Action.RETROCEDER, Action.REPETIR_COMIENZO -> numberModifiers
            Action.GIRAR_DERECHA, Action.GIRAR_IZQUIERDA -> angleModifiers
            Action.LEDS -> colorModifiers
            Action.TOCAR_CORCHEA, Action.TOCAR_MELODIA, Action.TOCAR_NEGRA -> musicModifiers
            else -> emptyList()
        }
    }
}