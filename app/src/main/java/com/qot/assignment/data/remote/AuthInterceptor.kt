package com.qot.assignment.data.remote

import com.qot.assignment.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = addRequest(chain.request())
        return chain.proceed(request)
    }

    private fun addRequest(request: Request): Request {
        val builder = request.newBuilder()
        builder.addHeader("app-id", BuildConfig.DUMMY_API_ID)
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Accept", "*/*")
        return builder.build()
    }

}