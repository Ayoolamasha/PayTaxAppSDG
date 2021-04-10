package com.ayoolamasha.paytaxappsdg.ApiCallBacks

sealed class ApiResult<out T : Any> {

    fun ApiResult(){}

    data class Success<out T : Any>(val response: T?) : ApiResult<T>()
    data class Error<out T : Any>(val response: T?) : ApiResult<T>()
    data class NetworkError(val response: Boolean?): ApiResult<Nothing>()
    data class Failure<out T : Any>(val response: T?) : ApiResult<T>()
    data class Exception(val t: Throwable): ApiResult<Nothing>()

}