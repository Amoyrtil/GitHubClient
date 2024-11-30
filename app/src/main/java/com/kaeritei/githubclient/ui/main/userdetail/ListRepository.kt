package com.kaeritei.githubclient.ui.main.userdetail

data class ListRepository(
    val name: String,
    val language: String,
    val starCount: Int,
    val description: String,
    val url: String,
)
