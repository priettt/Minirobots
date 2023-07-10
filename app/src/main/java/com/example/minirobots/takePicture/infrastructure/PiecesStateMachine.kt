package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.common.domain.Action
import com.example.minirobots.common.domain.Instruction
import com.example.minirobots.common.domain.Modifier
import com.example.minirobots.instructionsList.domain.actions.GetAvailableModifiers
import com.example.minirobots.instructionsList.domain.actions.IsSinglePieceInstruction
import com.example.minirobots.takePicture.infrastructure.State.*
import javax.inject.Inject

class PiecesStateMachine @Inject constructor(
    private val createInstructionWithRandomModifier: CreateInstructionWithRandomModifier,
    private val getAvailableModifiers: GetAvailableModifiers,
    private val isSinglePieceInstruction: IsSinglePieceInstruction
) {
    private var state: State = Base
    private val instructionList = mutableListOf<Instruction>()

    fun consumeAction(action: Action) {
        if (isSinglePieceInstruction(action)) {
            instructionList.add(Instruction(action, null))
            return
        }

        when (val currentState = state) {
            Base -> state = StoredAction(action)
            is StoredAction -> stayInStoredActionState(currentState.action, action)
            is StoredModifier -> goToBaseState(action, currentState.modifier)
        }
    }

    fun consumeModifier(modifier: Modifier) {
        when (val currentState = state) {
            Base -> state = StoredModifier(modifier)
            is StoredAction -> goToBaseState(currentState.action, modifier)
            is StoredModifier -> state = StoredModifier(modifier)
        }
    }

    fun end(): List<Instruction> {
        (state as? StoredAction)?.action?.let { instructionList.add(createInstructionWithRandomModifier(it)) }
        state = Base
        val result = instructionList.toList()
        instructionList.clear()
        return result
    }

    private fun goToBaseState(action: Action, modifier: Modifier) {
        instructionList.add(sanitizedInstruction(action, modifier))
        state = Base
    }

    private fun stayInStoredActionState(storedAction: Action, newAction: Action) {
        instructionList.add(createInstructionWithRandomModifier(storedAction))
        state = StoredAction(newAction)
    }

    private fun sanitizedInstruction(action: Action, modifier: Modifier): Instruction {
        return if (isModifierValid(action, modifier)) {
            Instruction(action, modifier)
        } else {
            createInstructionWithRandomModifier(action)
        }
    }

    private fun isModifierValid(action: Action, modifier: Modifier) = modifier in getAvailableModifiers(action)

}

sealed class State {
    object Base : State()
    data class StoredModifier(val modifier: Modifier) : State()
    data class StoredAction(val action: Action) : State()
}
