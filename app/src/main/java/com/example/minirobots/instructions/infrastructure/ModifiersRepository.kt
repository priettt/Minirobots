package com.example.minirobots.instructions.infrastructure

import com.example.minirobots.instructions.domain.entities.Modifier
import javax.inject.Inject

interface ModifiersRepository {
    fun get(): List<Modifier>
    fun store(modifiers: List<Modifier>)
}

class InMemoryModifiersRepository @Inject constructor() : ModifiersRepository {
    private var modifiers: List<Modifier> = listOf()

    override fun get() = modifiers
    override fun store(modifiers: List<Modifier>) {
        this.modifiers = modifiers
    }
}