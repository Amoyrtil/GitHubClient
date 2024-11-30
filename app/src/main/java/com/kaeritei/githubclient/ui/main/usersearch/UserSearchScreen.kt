package com.kaeritei.githubclient.ui.main.usersearch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaeritei.githubclient.ui.main.MainNavRoute

@Composable
fun UserSearchScreen(
    viewModel: UserSearchViewModel,
    onClickUser: (MainNavRoute.UserDetail) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserSearchScreen(
        uiState = uiState,
        onClickUser = {
            onClickUser(
                MainNavRoute.UserDetail(
                    userName = it.userName,
                ),
            )
        },
    )
}

@Composable
private fun UserSearchScreen(
    uiState: UserSearchUiState,
    onClickUser: (ListUser) -> Unit,
) {
    when (uiState) {
        is UserSearchUiState.Init -> {
            // Init
        }
        is UserSearchUiState.ContentIsReady -> {
            // ContentIsReady
        }
    }
}
