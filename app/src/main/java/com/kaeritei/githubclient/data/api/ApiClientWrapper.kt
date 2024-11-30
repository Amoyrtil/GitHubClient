package com.kaeritei.githubclient.data.api

import com.kaeritei.api.apis.RepositoryApi
import com.kaeritei.api.apis.SearchApi
import com.kaeritei.api.apis.UserApi
import com.kaeritei.api.infrastructure.ApiClient
import com.kaeritei.api.models.ListRepository
import com.kaeritei.api.models.SearchUserResponse
import com.kaeritei.api.models.UserDetailResponse
import com.kaeritei.githubclient.data.api.payload.UserSearchPayload
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClientWrapper
    @Inject
    constructor(
        private val apiClient: ApiClient,
    ) {
        suspend fun getUserQuerySearch(userSearchPayload: UserSearchPayload): Result<SearchUserResponse> =
            apiClient.createService(SearchApi::class.java).getUserQuerySearch(
                q = userSearchPayload.query,
                type = userSearchPayload.target.typeString,
            ).toResult()

        suspend fun getUserDetail(username: String): Result<UserDetailResponse> =
            apiClient.createService(UserApi::class.java).getUserDetail(
                username,
            ).toResult()

        suspend fun getUserRepositories(username: String): Result<List<ListRepository>> =
            apiClient.createService(RepositoryApi::class.java).getUserRepositories(
                username,
            ).toResult()

        private fun <T> Response<T>.toResult(): Result<T> {
            val code = code()
            val body = body()
            return if (isSuccessful && body != null) {
                Result.Success(body)
            } else {
                Result.Error(IOException("HTTP status code: $code"))
            }
        }
    }
