package com.example.minirobots.instructions.domain.actions

import com.example.minirobots.instructions.domain.entities.Modifier
import com.example.minirobots.instructions.infrastructure.ModifiersRepository
import javax.inject.Inject

class StoreModifiers @Inject constructor(
    private val modifiersRepository: ModifiersRepository
) {

    operator fun invoke(modifiers: List<Modifier>) {
        modifiersRepository.store(modifiers)
    }

}