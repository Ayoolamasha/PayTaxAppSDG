package com.ayoolamasha.paytaxappsdg.ApiCallBacks

data class ApiStatusResult<out T>(val STATUS: STATUS, val data: T?,
                                  val message: String?, val t: Throwable?, val response:Boolean?){
    companion object{

        fun <T> successResult(data: T?) : ApiStatusResult<T>{
            return ApiStatusResult(STATUS.SUCCESS, data, null, null, null)
        }

        fun <T> errorResult(message: String?, data:T?) : ApiStatusResult<T>{
            return ApiStatusResult(STATUS.ERROR, data, message, null, null)
        }

        fun <T> loading(data:T?): ApiStatusResult<T>{
            return ApiStatusResult(STATUS.LOADING, data, null, null, null)
        }

        fun <T> networkError(response: Boolean?, message: String?): ApiStatusResult<T> {
            return ApiStatusResult(STATUS.NETWORKERROR, null, message, null,response)

        }
        fun <T> exceptionResult(t:Throwable?, data:T?): ApiStatusResult<T>{
            return ApiStatusResult(STATUS.EXCEPTION, data, null, t, null)
        }

        fun <T> failureResult(message: String?, data:T?): ApiStatusResult<T>{
            return ApiStatusResult(STATUS.FAILURE, data, message, null, null)
        }
    }
}
