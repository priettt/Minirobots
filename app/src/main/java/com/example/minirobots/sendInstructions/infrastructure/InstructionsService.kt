package com.example.minirobots.sendInstructions.infrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InstructionsService {
    @POST("/program")
    suspend fun sendInstructions(@Body program: InstructionsRequest): Response<Unit>
}

@Serializable
data class PostInstructionResponse(val status: String)

@Serializable
data class InstructionsRequest(
    @SerialName("CMD")
    val program: String
)
