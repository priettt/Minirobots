package com.example.minirobots.instructions.domain.actions

import javax.inject.Inject

class GetInstructionsForSelection @Inject constructor() {

    operator fun invoke(): List<AddInstructionItem> {
        return listOf(
            AddInstructionItem("avanzar", "hola"),
            AddInstructionItem("nota", "hola"),
            AddInstructionItem("color", "hola"),
            AddInstructionItem("retroceder", "hola"),
            AddInstructionItem("girar izquierda", "hola"),
            AddInstructionItem("angulo", "hola"),
            AddInstructionItem("bajar lapiz", "hola"),
            AddInstructionItem("color amarillo", "hola"),
            AddInstructionItem("color", "hola"),
            AddInstructionItem("retroceder", "hola"),
            AddInstructionItem("girar izquierda", "hola"),
            AddInstructionItem("angulo", "hola"),
            AddInstructionItem("bajar lapiz", "hola"),
            AddInstructionItem("color amarillo", "hola"),
            AddInstructionItem("color", "hola"),
            AddInstructionItem("retroceder", "hola"),
            AddInstructionItem("girar izquierda", "hola"),
            AddInstructionItem("angulo", "hola"),
            AddInstructionItem("bajar lapiz", "hola"),
            AddInstructionItem("color amarillo", "hola"),
        )
    }

}
