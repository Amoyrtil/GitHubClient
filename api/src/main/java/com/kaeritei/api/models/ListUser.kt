package com.kaeritei.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * リストで返却されるユーザ情報向けのデータクラス
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
 *     "score": 1.0
 * }
 *  ```
 */
@JsonClass(generateAdapter = true)
data class ListUser(
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "login") val login: String,
)
