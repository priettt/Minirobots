package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.Instruction
import com.example.minirobots.takePicture.domain.entities.PieceName
import javax.inject.Inject

/*
    Receive a list of InstructionNames, and return a list of Instruction, with their actions and
    modifiers associated.
    The list comes ordered from left to right, but not vertically. That means that we expect the following cases:

    Happy case:
    Instruction names: [Derecha, 30 Grados, Avanzar, 2 Pasos, Retroceder, 3 Pasos, Retroceder, 4 Pasos, Izquierda, 36 Grados]

    Some modifiers inverted case:
    Instruction names: [30 Grados, Derecha, 2 Pasos, Avanzar, Retroceder, 3 Pasos, 4 Pasos, Retroceder, Izquierda, 36 Grados]

    Actions lost:
    Instruction names: [30 Grados, xx-Derecha-xx, 2 Pasos, Avanzar, xx-Retroceder-xx, 3 Pasos, 4 Pasos, Retroceder, Izquierda, 36 Grados]
    Instructions: [Avanzar 2, Retroceder 4, Izquierda 36]

    Modifiers lost:
    Instruction names: [xx-30 Grados-xx, Derecha, 2 Pasos, Avanzar, Retroceder, xx-3 Pasos-xx, 4 Pasos, Retroceder, Izquierda, 36 Grados]
    Instructions: [Derecha Random, Avanzar 2, Retroceder 4, Retroceder Random, Izquierda 36]

    Both lost:
    Instruction names: [30 Grados, xx-Derecha-xx, xx-2 Pasos-xx, Avanzar, Retroceder, xx-3 Pasos-xx, xx-4 Pasos-xx, Retroceder, Izquierda, 36 Grados]
    Instructions: [Avanzar Random, Retroceder Random, Retroceder 4, Retroceder Random, Izquierda 36]

    We should consider:
        - Edge cases (empty, only one component, only two)
        - What happens on the beginning of the list.
        - What happens on the end of the list.
        - What happens when there's actions without modifiers in the middle.
        - What happens when there's an action without an associated modifier.
        - What happens when there's a modifier without an associated action.

    [Avanzar, Retroceder, 2 Pasos, 3 Pasos]
    [2 Pasos, Avanzar, Retroceder, 3 Pasos]
    [Avanzar, 2 Pasos, Retroceder, 3 Pasos]
    [2 Pasos, Avanzar, 3 Pasos, Retroceder]
    [Avanzar, 2 Pasos, 3 Pasos, Retroceder]
    [2 Pasos, 2 Pasos, Avanzar, Retroceder]

 */

class PieceNamesMapper @Inject constructor(
    private val pieceMapper: PieceNameMapper,
    private val pieceNamesMapperStateMachine: PieceNameMapperStateMachine
) {
    fun map(pieceNames: List<PieceName>): List<Instruction> {
        val result = mutableListOf<Instruction>()
        for (pieceName in pieceNames) {
            val action = pieceMapper.mapToAction(pieceName)
            val modifier = pieceMapper.mapToModifier(pieceName)
            val instruction = pieceNamesMapperStateMachine.consumePiece(action, modifier)
            instruction?.let { result.add(it) }
        }
        val lastInstruction = pieceNamesMapperStateMachine.finish()
        lastInstruction?.let { result.add(it) }
        return result
    }
}
