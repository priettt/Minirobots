package com.example.minirobots.sendInstructions.infrastructure

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface InstructionsService {
    @Headers("Content-Type: application/json")
    @POST("/program")
    suspend fun sendInstructions(@Body program: String): Response<Unit>
}
