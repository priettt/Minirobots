package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.Action
import com.example.minirobots.Action.*
import com.example.minirobots.Instruction
import com.example.minirobots.Modifier.*
import javax.inject.Inject

class CreateInstructionWithRandomModifier @Inject constructor() {
    operator fun invoke(action: Action): Instruction = when (action) {
        AVANZAR -> Instruction(AVANZAR, NUMERO_AL_AZAR)
        BAJAR_LAPIZ -> Instruction(BAJAR_LAPIZ, null)
        FUNCION -> Instruction(FUNCION, null)
        FUNCION_COMIENZO -> Instruction(FUNCION_COMIENZO, null)
        FUNCION_FIN -> Instruction(FUNCION_FIN, null)
        GIRAR_DERECHA -> Instruction(GIRAR_DERECHA, ANGULO_AL_AZAR)
        GIRAR_IZQUIERDA -> Instruction(GIRAR_IZQUIERDA, ANGULO_AL_AZAR)
        LEDS -> Instruction(LEDS, COLOR_AL_AZAR)
        LEVANTAR_LAPIZ -> Instruction(LEVANTAR_LAPIZ, null)
        PROGRAMA_COMIENZO -> Instruction(PROGRAMA_COMIENZO, null)
        PROGRAMA_FIN -> Instruction(PROGRAMA_FIN, null)
        REPETIR_COMIENZO -> Instruction(REPETIR_COMIENZO, null)
        REPETIR_FIN -> Instruction(REPETIR_FIN, null)
        RETROCEDER -> Instruction(RETROCEDER, NUMERO_AL_AZAR)
        TOCAR_CORCHEA -> Instruction(TOCAR_CORCHEA, NOTA_AL_AZAR)
        TOCAR_MELODIA -> Instruction(TOCAR_MELODIA, NOTA_AL_AZAR)
        TOCAR_NEGRA -> Instruction(TOCAR_NEGRA, NOTA_AL_AZAR)
    }
}
