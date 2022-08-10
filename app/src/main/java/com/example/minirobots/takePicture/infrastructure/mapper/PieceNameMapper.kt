package com.example.minirobots.takePicture.infrastructure.mapper

import com.example.minirobots.common.domain.Action
import com.example.minirobots.common.domain.Modifier
import com.example.minirobots.takePicture.domain.entities.PieceName
import javax.inject.Inject

class PieceNameMapper @Inject constructor() {
    fun mapToAction(pieceName: PieceName): Action? = when (pieceName) {
        PieceName.AVANZAR -> Action.AVANZAR
        PieceName.BAJAR_LAPIZ -> Action.BAJAR_LAPIZ
        PieceName.FUNCION -> Action.FUNCION
        PieceName.FUNCION_COMIENZO -> Action.FUNCION_COMIENZO
        PieceName.FUNCION_FIN -> Action.FUNCION_FIN
        PieceName.GIRAR_DERECHA -> Action.GIRAR_DERECHA
        PieceName.GIRAR_IZQUIERDA -> Action.GIRAR_IZQUIERDA
        PieceName.LEDS -> Action.LEDS
        PieceName.LEVANTAR_LAPIZ -> Action.LEVANTAR_LAPIZ
        PieceName.PROGRAMA_COMIENZO -> Action.PROGRAMA_COMIENZO
        PieceName.PROGRAMA_FIN -> Action.PROGRAMA_FIN
        PieceName.REPETIR_COMIENZO -> Action.REPETIR_COMIENZO
        PieceName.REPETIR_FIN -> Action.REPETIR_FIN
        PieceName.RETROCEDER -> Action.RETROCEDER
        PieceName.TOCAR_CORCHEA -> Action.TOCAR_CORCHEA
        PieceName.TOCAR_MELODIA -> Action.TOCAR_MELODIA
        PieceName.TOCAR_NEGRA -> Action.TOCAR_NEGRA
        else -> null
    }

    fun mapToModifier(pieceName: PieceName): Modifier? = when (pieceName) {
        PieceName.ANGULO_30 -> Modifier.ANGULO_30
        PieceName.ANGULO_36 -> Modifier.ANGULO_36
        PieceName.ANGULO_45 -> Modifier.ANGULO_45
        PieceName.ANGULO_60 -> Modifier.ANGULO_60
        PieceName.ANGULO_72 -> Modifier.ANGULO_72
        PieceName.ANGULO_108 -> Modifier.ANGULO_108
        PieceName.ANGULO_120 -> Modifier.ANGULO_120
        PieceName.ANGULO_135 -> Modifier.ANGULO_135
        PieceName.ANGULO_144 -> Modifier.ANGULO_144
        PieceName.ANGULO_150 -> Modifier.ANGULO_150
        PieceName.ANGULO_AL_AZAR -> Modifier.ANGULO_AL_AZAR
        PieceName.COLOR_AL_AZAR -> Modifier.COLOR_AL_AZAR
        PieceName.COLOR_AMARILLO -> Modifier.COLOR_AMARILLO
        PieceName.COLOR_AZUL -> Modifier.COLOR_AZUL
        PieceName.COLOR_CELESTE -> Modifier.COLOR_CELESTE
        PieceName.COLOR_ROSA -> Modifier.COLOR_ROSA
        PieceName.COLOR_ROJO -> Modifier.COLOR_ROJO
        PieceName.COLOR_VERDE -> Modifier.COLOR_VERDE
        PieceName.NO_COLOR -> Modifier.NO_COLOR
        PieceName.NO_SONIDO -> Modifier.NO_SONIDO
        PieceName.NOTA_A -> Modifier.NOTA_A
        PieceName.NOTA_AL_AZAR -> Modifier.NOTA_AL_AZAR
        PieceName.NOTA_B -> Modifier.NOTA_B
        PieceName.NOTA_C -> Modifier.NOTA_C
        PieceName.NOTA_D -> Modifier.NOTA_D
        PieceName.NOTA_E -> Modifier.NOTA_E
        PieceName.NOTA_F -> Modifier.NOTA_F
        PieceName.NOTA_G -> Modifier.NOTA_G
        PieceName.NUMERO_2 -> Modifier.NUMERO_2
        PieceName.NUMERO_3 -> Modifier.NUMERO_3
        PieceName.NUMERO_4 -> Modifier.NUMERO_4
        PieceName.NUMERO_5 -> Modifier.NUMERO_5
        PieceName.NUMERO_6 -> Modifier.NUMERO_6
        PieceName.NUMERO_AL_AZAR -> Modifier.NUMERO_AL_AZAR
        else -> null
    }
}
