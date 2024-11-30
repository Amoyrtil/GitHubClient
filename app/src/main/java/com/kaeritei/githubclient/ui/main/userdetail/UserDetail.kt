package com.kaeritei.githubclient.ui.main.userdetail

data class UserDetail(
    val thumbnailUrl: String,
    val userName: String,
    val fullName: String?,
    val followersCount: Int,
    val followingCount: Int,
)
