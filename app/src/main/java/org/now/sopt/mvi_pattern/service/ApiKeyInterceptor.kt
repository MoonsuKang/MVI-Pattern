package org.now.sopt.mvi_pattern.service

import okhttp3.Interceptor
import okhttp3.Response
import org.now.sopt.mvi_pattern.BuildConfig

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Client-ID ${BuildConfig.UNSPLASH_API_KEY}")
            .build()
        return chain.proceed(request)
    }
}