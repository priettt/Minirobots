package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.common.domain.Action
import com.example.minirobots.common.domain.Action.*
import com.example.minirobots.common.domain.Instruction
import com.example.minirobots.common.domain.Modifier.*
import javax.inject.Inject

class CreateInstructionWithRandomModifier @Inject constructor() {
    operator fun invoke(action: Action): Instruction = when (action) {
        AVANZAR, RETROCEDER, REPETIR_COMIENZO -> Instruction(action, NUMERO_AL_AZAR)
        GIRAR_DERECHA, GIRAR_IZQUIERDA -> Instruction(action, ANGULO_AL_AZAR)
        LEDS -> Instruction(LEDS, COLOR_AL_AZAR)
        TOCAR_CORCHEA, TOCAR_MELODIA, TOCAR_NEGRA -> Instruction(action, NOTA_AL_AZAR)
        else -> Instruction(action, null)
    }
}
