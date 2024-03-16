package com.reviro.common.base.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val throwable: Throwable, var handled: Boolean = false) : ApiResult<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(throwable: Throwable) = Error(throwable)

    }
}

suspend fun <T> apiRequest(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(
            withContext(Dispatchers.IO) { apiCall() }
        )
    } catch (ex: Exception) {
        ApiResult.Error(ex)
    }
}


inline fun <reified T, reified R> ApiResult<T>.map(transform: (T) -> R): ApiResult<R> {
    return when (this) {
        is ApiResult.Success -> ApiResult.success(transform(data))
        is ApiResult.Error -> this
    }
}


