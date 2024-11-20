package com.tisenres.yandextodoapp.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val tokenProvider: () -> String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "OAuth ${tokenProvider()}")
//            .addHeader("X-Last-Known-Revision", revisionProvider().toString())
            .build()
        return chain.proceed(request)
    }
}