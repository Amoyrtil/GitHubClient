package com.kaeritei.api.apis

import com.kaeritei.api.models.UserDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    /**
     * ユーザーの詳細情報を取得する
     * Responses:
     *  - 200: OK
     *  - 404: Not Found
     *
     * @param username ユーザー名
     * @return [UserDetailResponse]
     * @see <a href="https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#get-a-user">Get a user - GitHub Docs</a>
     */
    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String,
    ): Response<UserDetailResponse>
}
