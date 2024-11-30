package com.kaeritei.api.apis

import com.kaeritei.api.models.ListRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryApi {
    /**
     * ユーザーのリポジトリ一覧を取得する
     * Responses:
     *  - 200: OK
     *  - 404: Not Found
     *
     * @param username ユーザー名
     * @return [ListRepository]
     */
    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
    ): Response<List<ListRepository>>
}
