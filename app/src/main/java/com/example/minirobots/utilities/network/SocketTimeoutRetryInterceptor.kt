package com.example.minirobots.utilities.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/* Retry requests until they succeed, or run out of tries.

This interceptor swallows exceptions, and returns an error response instead. We could improve this, but for now it's not needed.
*/

class SocketTimeoutRetryInterceptor(private val maxRetries: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = getResponse(chain)
        var retries = 0

        while (!response.isSuccessful && retries < maxRetries) {
            Log.d("RetryInterceptor", "Retry number $retries")
            Log.d("RetryInterceptor", "Error: ${response.message}")
            response.close()
            response = getResponse(chain)
            retries++
        }
        return response
    }

    private fun getResponse(chain: Interceptor.Chain) =
        try {
            chain.proceed(chain.request())
        } catch (exception: Exception) {
            getErrorResponse(chain, exception)
        }

    private fun getErrorResponse(chain: Interceptor.Chain, exception: Exception): Response {
        val errorMessage = exception.localizedMessage ?: "Intercepted Exception"
        return Response.Builder()
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .code(500)
            .message(errorMessage)
            .body(errorMessage.toResponseBody())
            .build()
    }

}