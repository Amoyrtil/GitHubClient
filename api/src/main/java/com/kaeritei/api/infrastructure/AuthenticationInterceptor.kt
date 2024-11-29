package com.kaeritei.api.infrastructure

import com.kaeritei.api.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor
    @Inject
    constructor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val personalAccessToken = BuildConfig.PERSONAL_ACCESS_TOKEN
            val request =
                chain.request().newBuilder().apply {
                    addHeader("Authorization", personalAccessToken)
                }.build()

            return chain.proceed(request)
        }
    }
