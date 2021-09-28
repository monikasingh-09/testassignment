package com.qot.assignment.data.remote

import retrofit2.Response
import java.net.UnknownHostException

sealed class ApiResult<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : ApiResult<T>()
    data class Error(val errorCode: Int, val message: String) : ApiResult<Nothing>()

    companion object {
        suspend fun <T> callApi(call: suspend () -> Response<T>): ApiResult<T> {
            return try {
                val response: Response<T> = call.invoke()
                if (response.isSuccessful)
                    Success(response.body()!!)
                else
                    Error(response.code(), "Something went wrong")

            } catch (e: Exception) {
                if (e is UnknownHostException)
                    Error(404, "No internet connection")
                else
                    Error(500, "Something went wrong!")
            }
        }
    }
}