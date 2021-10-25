package com.example.minirobots.takePicture.infrastructure

import com.example.minirobots.Action
import com.example.minirobots.Instruction
import com.example.minirobots.Modifier
import javax.inject.Inject

class PieceNameMapperStateMachine @Inject constructor(
    private val createInstructionWithRandomModifier: CreateInstructionWithRandomModifier,
) {
    private var state: State = State.Base

    fun consumePiece(action: Action?, modifier: Modifier?): Instruction? = when {
        action != null -> consumeAction(action)
        modifier != null -> consumeModifier(modifier)
        else -> null
    }

    fun finish(): Instruction? = when (val state = state) {
        is State.StoredAction -> createInstructionWithRandomModifier(state.action)
        else -> null
    }

    private fun consumeAction(action: Action): Instruction? {
        if (action.isSinglePieceInstruction())
            return Instruction(action, null)
        return when (val state = state) {
            State.Base -> goToStoredActionState(action)
            is State.StoredAction -> stayInStoredActionState(state.action, action)
            is State.StoredModifier -> goToBaseState(action, state.modifier)
        }
    }

    private fun consumeModifier(modifier: Modifier): Instruction? {
        return when (val state = state) {
            State.Base -> goToStoredModifierState(modifier)
            is State.StoredAction -> goToBaseState(state.action, modifier)
            is State.StoredModifier -> goToStoredModifierState(modifier)
        }
    }

    private fun goToBaseState(action: Action, modifier: Modifier): Instruction {
        state = State.Base
        return Instruction(action, modifier)
    }

    private fun goToStoredModifierState(modifier: Modifier): Instruction? {
        state = State.StoredModifier(modifier)
        return null
    }

    private fun goToStoredActionState(action: Action): Instruction? {
        state = State.StoredAction(action)
        return null
    }

    private fun stayInStoredActionState(storedAction: Action, consumedAction: Action): Instruction {
        state = State.StoredAction(consumedAction)
        return createInstructionWithRandomModifier(storedAction)
    }

    private fun Action.isSinglePieceInstruction() = this in listOf(
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

sealed class State {
    object Base : State()
    data class StoredModifier(val modifier: Modifier) : State()
    data class StoredAction(val action: Action) : State()
}