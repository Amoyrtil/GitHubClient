package com.kaeritei.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * リポジトリの一覧情報向けのデータクラス
 * 以下のようなJSONに対応するクラスとして利用する想定
 *
 * ```json
 * {
 *     "id": 895840748,
 *     "node_id": "R_kgDONWVx7A",
 *     "name": "GitHubClient",
 *     "full_name": "Amoyrtil/GitHubClient",
 *     "private": false,
 *     "owner": {
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
 *     "site_admin": false
 *     },
 *     "html_url": "https://github.com/Amoyrtil/GitHubClient",
 *     "description": null,
 *     "fork": false,
 *     "url": "https://api.github.com/repos/Amoyrtil/GitHubClient",
 *     "forks_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/forks",
 *     "keys_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/keys{/key_id}",
 *     "collaborators_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/collaborators{/collaborator}",
 *     "teams_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/teams",
 *     "hooks_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/hooks",
 *     "issue_events_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/issues/events{/number}",
 *     "events_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/events",
 *     "assignees_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/assignees{/user}",
 *     "branches_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/branches{/branch}",
 *     "tags_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/tags",
 *     "blobs_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/git/blobs{/sha}",
 *     "git_tags_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/git/tags{/sha}",
 *     "git_refs_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/git/refs{/sha}",
 *     "trees_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/git/trees{/sha}",
 *     "statuses_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/statuses/{sha}",
 *     "languages_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/languages",
 *     "stargazers_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/stargazers",
 *     "contributors_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/contributors",
 *     "subscribers_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/subscribers",
 *     "subscription_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/subscription",
 *     "commits_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/commits{/sha}",
 *     "git_commits_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/git/commits{/sha}",
 *     "comments_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/comments{/number}",
 *     "issue_comment_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/issues/comments{/number}",
 *     "contents_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/contents/{+path}",
 *     "compare_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/compare/{base}...{head}",
 *     "merges_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/merges",
 *     "archive_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/{archive_format}{/ref}",
 *     "downloads_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/downloads",
 *     "issues_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/issues{/number}",
 *     "pulls_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/pulls{/number}",
 *     "milestones_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/milestones{/number}",
 *     "notifications_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/notifications{?since,all,participating}",
 *     "labels_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/labels{/name}",
 *     "releases_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/releases{/id}",
 *     "deployments_url": "https://api.github.com/repos/Amoyrtil/GitHubClient/deployments",
 *     "created_at": "2024-11-29T02:46:29Z",
 *     "updated_at": "2024-11-29T11:19:29Z",
 *     "pushed_at": "2024-11-29T11:19:25Z",
 *     "git_url": "git://github.com/Amoyrtil/GitHubClient.git",
 *     "ssh_url": "git@github.com:Amoyrtil/GitHubClient.git",
 *     "clone_url": "https://github.com/Amoyrtil/GitHubClient.git",
 *     "svn_url": "https://github.com/Amoyrtil/GitHubClient",
 *     "homepage": null,
 *     "size": 113,
 *     "stargazers_count": 0,
 *     "watchers_count": 0,
 *     "language": "Kotlin",
 *     "has_issues": true,
 *     "has_projects": true,
 *     "has_downloads": true,
 *     "has_wiki": true,
 *     "has_pages": false,
 *     "has_discussions": false,
 *     "forks_count": 0,
 *     "mirror_url": null,
 *     "archived": false,
 *     "disabled": false,
 *     "open_issues_count": 1,
 *     "license": null,
 *     "allow_forking": true,
 *     "is_template": false,
 *     "web_commit_signoff_required": false,
 *     "topics": [
 *
 *     ],
 *     "visibility": "public",
 *     "forks": 0,
 *     "open_issues": 1,
 *     "watchers": 0,
 *     "default_branch": "master"
 * },
 * ```
 */
@JsonClass(generateAdapter = true)
data class ListRepository(
    @Json(name = "name") val name: String,
    @Json(name = "language") val language: String?,
    @Json(name = "stargazers_count") val stargazersCount: Int,
    @Json(name = "description") val description: String?,
    @Json(name = "fork") val fork: Boolean,
    @Json(name = "html_url") val htmlUrl: String,
)