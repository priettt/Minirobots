package com.example.minirobots.home.domain

data class Instruction(private val type: InstructionType)

enum class InstructionType {
    TURN_LEFT,
    TURN_RIGHT,
    FORWARD,
    BACKWARD
}