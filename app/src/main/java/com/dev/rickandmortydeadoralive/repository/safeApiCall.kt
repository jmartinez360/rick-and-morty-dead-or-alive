package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.api.ApiResult
import java.io.IOException

suspend fun <T : Any> safeApiCall(call: suspend () -> ApiResult<T>, errorMessage: String): ApiResult<T> = try {
    call.invoke()
} catch (e: Exception) {
    ApiResult.Error(IOException(errorMessage, e))
}

val <T> T.exhaustive: T get() = this