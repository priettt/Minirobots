package com.example.minirobots.di

import com.example.minirobots.home.domain.InstructionsRecognizer
import com.example.minirobots.home.domain.MlKitInstructionsRecognizer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * As these types are scoped to the application lifecycle using @Singleton, they're installed
 * in Hilt's ApplicationComponent.
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class InstructionsRecognizerModule {

    @Singleton
    @Binds
    abstract fun bindInstructionsRecognizer(
        mlKitInstructionsRecognizer: MlKitInstructionsRecognizer
    ): InstructionsRecognizer
}
