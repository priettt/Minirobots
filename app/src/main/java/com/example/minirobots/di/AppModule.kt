package com.example.minirobots.di

import com.example.minirobots.sendInstructions.infrastructure.InstructionsParser
import com.example.minirobots.sendInstructions.infrastructure.InstructionsParserImpl
import com.example.minirobots.sendInstructions.infrastructure.InstructionsService
import com.example.minirobots.takePicture.infrastructure.InMemoryInstructionsRepository
import com.example.minirobots.takePicture.infrastructure.InstructionsRepository
import com.example.minirobots.takePicture.infrastructure.LevenshteinDistanceCalculator
import com.example.minirobots.takePicture.infrastructure.StringDistanceCalculator
import com.example.minirobots.utilities.network.SocketTimeoutRetryInterceptor
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

private const val MINIROBOTS_API_BASE_URL = "http://192.168.4.1"
private const val MAX_RETRIES = 3

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

@Module
@InstallIn(SingletonComponent::class)
abstract class InstructionsParserModule {

    @Singleton
    @Binds
    abstract fun bindInstructionsParser(
        instructionsParserImpl: InstructionsParserImpl
    ): InstructionsParser
}

@Module
@InstallIn(SingletonComponent::class)
object TextRecognizerModule {

    @Provides
    @Singleton
    fun provideTextRecognizer() = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val json = Json { ignoreUnknownKeys = true }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideJsonConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(SocketTimeoutRetryInterceptor(MAX_RETRIES))
            .build()
    }

    @Provides
    @Singleton
    fun provideInstructionsService(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): InstructionsService {
        return Retrofit.Builder()
            .baseUrl(MINIROBOTS_API_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
            .create(InstructionsService::class.java)
    }

}