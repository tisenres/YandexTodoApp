package com.tisenres.yandextodoapp.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            when (response.code) {
                400 -> throw Exception("Bad Request")
                401 -> throw Exception("Unauthorized")
                403 -> throw Exception("Forbidden")
                404 -> throw Exception("Not Found")
                500 -> throw Exception("Internal Server Error")
                else -> throw Exception("Unknown Error: ${response.code}")
            }
        }
        return response
    }
}
