package com.kaeritei.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * ユーザーの詳細情報向けのデータクラス
 * 以下のようなJSONに対応するクラスとして利用する想定
 * ```json
 * {
 *     "login": "Amoyrtil",
 *     "id": 32154375,
 *     "node_id": "MDQ6VXNlcjMyMTU0Mzc1",
 *     "avatar_url": "https://avatars.githubusercontent.com/u/32154375?v=4",
 *     "gravatar_id": "",
 *     "url": "https://api.github.com/users/Amoyrtil",
 *     "html_url": "https://github.com/Amoyrtil",
 *     "followers_url": "https://api.github.com/users/Amoyrtil/followers",
 *     "following_url": "https://api.github.com/users/Amoyrtil/following{/other_user}",
 *     "gists_url": "https://api.github.com/users/Amoyrtil/gists{/gist_id}",
 *     "starred_url": "https://api.github.com/users/Amoyrtil/starred{/owner}{/repo}",
 *     "subscriptions_url": "https://api.github.com/users/Amoyrtil/subscriptions",
 *     "organizations_url": "https://api.github.com/users/Amoyrtil/orgs",
 *     "repos_url": "https://api.github.com/users/Amoyrtil/repos",
 *     "events_url": "https://api.github.com/users/Amoyrtil/events{/privacy}",
 *     "received_events_url": "https://api.github.com/users/Amoyrtil/received_events",
 *     "type": "User",
 *     "user_view_type": "public",
 *     "site_admin": false,
 *     "name": "Ryoma Shibata",
 *     "company": "Cluster, Inc.",
 *     "blog": "",
 *     "location": "Tokyo",
 *     "email": null,
 *     "hireable": null,
 *     "bio": null,
 *     "twitter_username": "amoyrtil",
 *     "public_repos": 2,
 *     "public_gists": 0,
 *     "followers": 3,
 *     "following": 3,
 *     "created_at": "2017-09-21T04:58:16Z",
 *     "updated_at": "2024-11-26T12:36:22Z"
 * }
 * ```
 */
@JsonClass(generateAdapter = true)
data class UserDetailResponse(
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "login") val login: String,
    @Json(name = "name") val name: String?,
    @Json(name = "followers") val followers: Int,
    @Json(name = "following") val following: Int,
)
