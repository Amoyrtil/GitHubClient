package com.kaeritei.api.di

import android.content.Context
import com.kaeritei.api.R
import com.kaeritei.api.infrastructure.ApiClient
import com.kaeritei.api.infrastructure.AuthenticationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApiModule  {
    @Provides
    fun provideApiClient(
        @ApplicationContext context: Context,
        authenticationInterceptor: AuthenticationInterceptor,
    ): ApiClient {
        val baseUrl = context.getString(R.string.github_api_endpoint)
        return ApiClient(
            baseUrl = baseUrl,
            authenticationInterceptor = authenticationInterceptor,
        )
    }
}
