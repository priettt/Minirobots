package com.example.minirobots.instructionsList.domain.entities

import java.util.*

data class UIInstruction(
    val action: UIAction,
    val modifier: UIModifier?,
    val id: UUID,
)

data class UIAction(
    val name: String,
    val imageDrawable: Int
)

data class UIModifier(
    val name: String,
    val imageDrawable: Int
)