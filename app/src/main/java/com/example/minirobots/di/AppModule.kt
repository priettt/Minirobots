package com.example.minirobots.di

import com.example.minirobots.home.domain.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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
@InstallIn(ApplicationComponent::class)
object DispatcherModule {
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Module
@InstallIn(ApplicationComponent::class)
abstract class InstructionsServiceModule {

    @Singleton
    @Binds
    abstract fun bindInstructionsService(
        mlKitInstructionsService: MlKitInstructionsService
    ): InstructionsService
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class InstructionRecognizerModule {

    @Singleton
    @Binds
    abstract fun bindInstructionRecognizer(
        distanceInstructionRecognizer: StringDistanceInstructionRecognizer
    ): InstructionRecognizer
}


@Module
@InstallIn(ApplicationComponent::class)
abstract class StringDistanceCalculatorModule {
    @Singleton
    @Binds
    abstract fun bindStringDistanceCalculator(
        levenshteinDistanceCalculator: LevenshteinDistanceCalculator
    ): StringDistanceCalculator
}
