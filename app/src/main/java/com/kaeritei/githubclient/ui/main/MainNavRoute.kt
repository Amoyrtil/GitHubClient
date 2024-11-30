package com.kaeritei.githubclient.ui.main

import kotlinx.serialization.Serializable

sealed interface MainNavRoute {
    @Serializable
    data object UserSearch : MainNavRoute

    @Serializable
    data class UserDetail(val userName: String) : MainNavRoute
}
