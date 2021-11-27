package com.example.minirobots.instructionsList.infrastructure

import com.example.minirobots.Action
import com.example.minirobots.Instruction
import com.example.minirobots.Modifier
import com.example.minirobots.R
import com.example.minirobots.instructionsList.domain.entities.UIAction
import com.example.minirobots.instructionsList.domain.entities.UIInstruction
import com.example.minirobots.instructionsList.domain.entities.UIModifier
import javax.inject.Inject

class UIInstructionsMapper @Inject constructor(
    private val actionMapper: UIActionMapper,
    private val modifierMapper: UIModifierMapper,
) {
    fun map(instructions: List<Instruction>): List<UIInstruction> {
        return instructions.map {
            mapInstruction(it)
        }
    }

    private fun mapInstruction(instruction: Instruction) = UIInstruction(
        actionMapper.map(instruction.action),
        modifierMapper.map(instruction.modifier),
        instruction.id
    )
}

class UIActionMapper @Inject constructor() {
    fun map(action: Action) = when (action) {
        Action.AVANZAR -> UIAction("Avanzar", R.drawable.adelante)
        Action.BAJAR_LAPIZ -> UIAction("Bajar lápiz", R.drawable.lapiz_abajo)
        Action.FUNCION -> UIAction("Ejecutar función", R.drawable.funcion)
        Action.FUNCION_COMIENZO -> UIAction("Inicio de función", R.drawable.inicio_funcion)
        Action.FUNCION_FIN -> UIAction("Fin de función", R.drawable.fin_funcion)
        Action.GIRAR_DERECHA -> UIAction("Girar a la derecha", R.drawable.girar_derecha)
        Action.GIRAR_IZQUIERDA -> UIAction("Girar a la izquierda", R.drawable.girar_izquierda)
        Action.LEDS -> UIAction("Leds", R.drawable.leds)
        Action.LEVANTAR_LAPIZ -> UIAction("Levantar lápiz", R.drawable.lapiz_arriba)
        Action.PROGRAMA_COMIENZO -> UIAction("Inicio del programa", R.drawable.programa_comienzo)
        Action.PROGRAMA_FIN -> UIAction("Fin del programa", R.drawable.programa_fin)
        Action.REPETIR_COMIENZO -> UIAction("Inicio de repetición", R.drawable.repetir_comienzo)
        Action.REPETIR_FIN -> UIAction("Fin de repetición", R.drawable.repetir_fin)
        Action.RETROCEDER -> UIAction("Retroceder", R.drawable.atras)
        Action.TOCAR_CORCHEA -> UIAction("Reproducir corchea", R.drawable.corchea)
        Action.TOCAR_MELODIA -> UIAction("Reproducir melodía", R.drawable.melodia)
        Action.TOCAR_NEGRA -> UIAction("Reproducir negra", R.drawable.negra)
    }

    fun map(uiAction: UIAction) = when (uiAction.name) {
        "Avanzar" -> Action.AVANZAR
        "Bajar lápiz" -> Action.BAJAR_LAPIZ
        "Ejecutar función" -> Action.FUNCION
        "Inicio de función" -> Action.FUNCION_COMIENZO
        "Fin de función" -> Action.FUNCION_FIN
        "Girar a la derecha" -> Action.GIRAR_DERECHA
        "Girar a la izquierda" -> Action.GIRAR_IZQUIERDA
        "Leds" -> Action.LEDS
        "Levantar lápiz" -> Action.LEVANTAR_LAPIZ
        "Inicio del programa" -> Action.PROGRAMA_COMIENZO
        "Fin del programa" -> Action.PROGRAMA_FIN
        "Inicio de repetición" -> Action.REPETIR_COMIENZO
        "Fin de repetición" -> Action.REPETIR_FIN
        "Retroceder" -> Action.RETROCEDER
        "Reproducir corchea" -> Action.TOCAR_CORCHEA
        "Reproducir melodía" -> Action.TOCAR_MELODIA
        "Reproducir negra" -> Action.TOCAR_NEGRA
        else -> null
    }
}

class UIModifierMapper @Inject constructor() {
    fun map(modifier: Modifier?) = when (modifier) {
        Modifier.ANGULO_30 -> UIModifier("30 grados", R.drawable.angulo_30)
        Modifier.ANGULO_36 -> UIModifier("36 grados", R.drawable.angulo_36)
        Modifier.ANGULO_45 -> UIModifier("45 grados", R.drawable.angulo_45)
        Modifier.ANGULO_60 -> UIModifier("60 grados", R.drawable.angulo_60)
        Modifier.ANGULO_72 -> UIModifier("72 grados", R.drawable.angulo_72)
        Modifier.ANGULO_108 -> UIModifier("108 grados", R.drawable.angulo_108)
        Modifier.ANGULO_120 -> UIModifier("120 grados", R.drawable.angulo_120)
        Modifier.ANGULO_135 -> UIModifier("135 grados", R.drawable.angulo_135)
        Modifier.ANGULO_144 -> UIModifier("144 grados", R.drawable.angulo_144)
        Modifier.ANGULO_150 -> UIModifier("150 grados", R.drawable.angulo_150)
        Modifier.ANGULO_AL_AZAR -> UIModifier("Ángulo al azar", R.drawable.angulo_al_azar)
        Modifier.COLOR_AL_AZAR -> UIModifier("Color al azar", R.drawable.color_al_azar)
        Modifier.COLOR_AMARILLO -> UIModifier("Color amarillo", R.drawable.color_amarillo)
        Modifier.COLOR_AZUL -> UIModifier("Color azul", R.drawable.color_azul)
        Modifier.COLOR_CELESTE -> UIModifier("Color celeste", R.drawable.color_celeste)
        Modifier.COLOR_ROSA -> UIModifier("Color rosa", R.drawable.color_rosa)
        Modifier.COLOR_ROJO -> UIModifier("Color rojo", R.drawable.color_rojo)
        Modifier.COLOR_VERDE -> UIModifier("Color verde", R.drawable.color_verde)
        Modifier.NO_COLOR -> UIModifier("Apagar leds", R.drawable.color_apagado)
        Modifier.NO_SONIDO -> UIModifier("Silencio", R.drawable.silencio)
        Modifier.NOTA_A -> UIModifier("A - LA", R.drawable.nota_a)
        Modifier.NOTA_AL_AZAR -> UIModifier("Nota al azar", R.drawable.nota_al_azar)
        Modifier.NOTA_B -> UIModifier("B - SI", R.drawable.nota_b)
        Modifier.NOTA_C -> UIModifier("C - DO", R.drawable.nota_c)
        Modifier.NOTA_D -> UIModifier("D - RE", R.drawable.nota_d)
        Modifier.NOTA_E -> UIModifier("E - MI", R.drawable.nota_e)
        Modifier.NOTA_F -> UIModifier("F - FA", R.drawable.nota_f)
        Modifier.NOTA_G -> UIModifier("G - SOL", R.drawable.nota_g)
        Modifier.NUMERO_2 -> UIModifier("Número 2", R.drawable.numero_2)
        Modifier.NUMERO_3 -> UIModifier("Número 3", R.drawable.numero_3)
        Modifier.NUMERO_4 -> UIModifier("Número 4", R.drawable.numero_4)
        Modifier.NUMERO_5 -> UIModifier("Número 5", R.drawable.numero_5)
        Modifier.NUMERO_6 -> UIModifier("Número 6", R.drawable.numero_6)
        Modifier.NUMERO_AL_AZAR -> UIModifier("Número al azar", R.drawable.numero_al_azar)
        else -> null
    }

    fun map(modifier: UIModifier) = when (modifier.name) {
        "30 grados" -> Modifier.ANGULO_30
        "36 grados" -> Modifier.ANGULO_36
        "45 grados" -> Modifier.ANGULO_45
        "60 grados" -> Modifier.ANGULO_60
        "72 grados" -> Modifier.ANGULO_72
        "108 grados" -> Modifier.ANGULO_108
        "120 grados" -> Modifier.ANGULO_120
        "135 grados" -> Modifier.ANGULO_135
        "144 grados" -> Modifier.ANGULO_144
        "150 grados" -> Modifier.ANGULO_150
        "Ángulo al azar" -> Modifier.ANGULO_AL_AZAR
        "Color al azar" -> Modifier.COLOR_AL_AZAR
        "Color amarillo" -> Modifier.COLOR_AMARILLO
        "Color azul" -> Modifier.COLOR_AZUL
        "Color celeste" -> Modifier.COLOR_CELESTE
        "Color rosa" -> Modifier.COLOR_ROSA
        "Color rojo" -> Modifier.COLOR_ROJO
        "Color verde" -> Modifier.COLOR_VERDE
        "Apagar leds" -> Modifier.NO_COLOR
        "Silencio" -> Modifier.NO_SONIDO
        "A - LA" -> Modifier.NOTA_A
        "Nota al azar" -> Modifier.NOTA_AL_AZAR
        "B - SI" -> Modifier.NOTA_B
        "C - DO" -> Modifier.NOTA_C
        "D - RE" -> Modifier.NOTA_D
        "E - MI" -> Modifier.NOTA_E
        "F - FA" -> Modifier.NOTA_F
        "G - SOL" -> Modifier.NOTA_G
        "Número 2" -> Modifier.NUMERO_2
        "Número 3" -> Modifier.NUMERO_3
        "Número 4" -> Modifier.NUMERO_4
        "Número 5" -> Modifier.NUMERO_5
        "Número 6" -> Modifier.NUMERO_6
        "Número al azar" -> Modifier.NUMERO_AL_AZAR
        else -> null
    }
}