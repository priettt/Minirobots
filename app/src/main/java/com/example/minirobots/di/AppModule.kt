package com.example.minirobots.di

import com.example.minirobots.home.domain.InstructionRecognizer
import com.example.minirobots.home.domain.LevenshteinDistanceCalculator
import com.example.minirobots.home.domain.StringDistanceCalculator
import com.example.minirobots.home.domain.StringDistanceInstructionRecognizer
import com.example.minirobots.home.infrastructure.InMemoryInstructionsRepository
import com.example.minirobots.home.infrastructure.InstructionsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * As these types are scoped to the application lifecycle using @Singleton, they're installed
 * in Hilt's ApplicationComponent.
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Module
@InstallIn(SingletonComponent::class)
abstract class InstructionRecognizerModule {

    @Singleton
    @Binds
    abstract fun bindInstructionRecognizer(
        distanceInstructionRecognizer: StringDistanceInstructionRecognizer
    ): InstructionRecognizer
}


@Module
@InstallIn(SingletonComponent::class)
abstract class StringDistanceCalculatorModule {
    @Singleton
    @Binds
    abstract fun bindStringDistanceCalculator(
        levenshteinDistanceCalculator: LevenshteinDistanceCalculator
    ): StringDistanceCalculator
}

@Module
@InstallIn(SingletonComponent::class)
abstract class InstructionsRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindInstructionsRepository(
        inMemoryInstructionsRepository: InMemoryInstructionsRepository
    ): InstructionsRepository
}