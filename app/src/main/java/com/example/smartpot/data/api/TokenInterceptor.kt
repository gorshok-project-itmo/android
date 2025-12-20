package com.example.smartpot.data.api

import com.example.smartpot.data.repository.TokenProvider
import okhttp3.Interceptor

class TokenInterceptor(private val provider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val token = provider.getToken()
        val request = if (!token.isNullOrBlank()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else chain.request()
        return chain.proceed(request)
    }
}