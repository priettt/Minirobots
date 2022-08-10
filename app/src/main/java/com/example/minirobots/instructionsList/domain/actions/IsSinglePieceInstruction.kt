package com.example.minirobots.instructionsList.domain.actions

import com.example.minirobots.common.domain.Action
import javax.inject.Inject

class IsSinglePieceInstruction @Inject constructor() {

    operator fun invoke(action: Action): Boolean = action in listOf(
        Action.BAJAR_LAPIZ,
        Action.FUNCION,
        Action.FUNCION_COMIENZO,
        Action.FUNCION_FIN,
        Action.LEVANTAR_LAPIZ,
        Action.PROGRAMA_COMIENZO,
        Action.PROGRAMA_FIN,
        Action.REPETIR_COMIENZO,
        Action.REPETIR_FIN,
    )

}