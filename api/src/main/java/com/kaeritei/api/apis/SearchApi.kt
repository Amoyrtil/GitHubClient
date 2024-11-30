package com.kaeritei.api.apis

import com.kaeritei.api.models.SearchUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    /**
     * ユーザーの検索を行う
     * Responses:
     *  - 200: OK
     *  - 400: Bad Request
     *
     * @param q 検索のクエリ
     * @param type 検索対象の種別 user or organization を指定する
     * @return [SearchUserResponse]
     * @see <a href="https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-users">Search users - GitHub Docs</a>
     */
    @GET("search/users")
    suspend fun getUserQuerySearch(
        @Query("q") q: String,
        @Query("type") type: String,
    ): Response<SearchUserResponse>
}
