package com.kaeritei.githubclient.ui.main.userdetail

sealed interface UserDetailDestination {
    data class OpenBrowser(
        val url: String,
    ) : UserDetailDestination
}
