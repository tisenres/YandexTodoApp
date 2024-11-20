package com.tisenres.yandextodoapp.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            val errorBody = response.body.string()
            response.close()
            when (response.code) {

                400 -> throw BadRequestException(errorBody)
                401 -> throw UnauthorizedException(errorBody)
                403 -> throw Exception("Forbidden")
                404 -> throw Exception("Not Found")
                500 -> throw Exception("Internal Server Error")
                else -> throw Exception("Unknown Error: ${response.code}")
            }
        }
        return response
    }
}

class BadRequestException(message: String) : IOException(message)
class UnauthorizedException(message: String) : IOException(message)