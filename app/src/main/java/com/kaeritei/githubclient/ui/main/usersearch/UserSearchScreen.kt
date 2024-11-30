package com.kaeritei.githubclient.ui.main.usersearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaeritei.githubclient.ui.main.MainNavRoute
import com.kaeritei.githubclient.ui.main.usersearch.compose.UserSearchContent
import com.kaeritei.githubclient.ui.main.usersearch.compose.UserSearchContentEmpty
import com.kaeritei.githubclient.ui.main.usersearch.compose.UserSearchInput

@Composable
fun UserSearchScreen(
    viewModel: UserSearchViewModel,
    onClickUser: (MainNavRoute.UserDetail) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserSearchScreen(
        uiState = uiState,
        onUpdateQuery = {
            viewModel.updateQueryText(it)
        },
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
    onUpdateQuery: (String) -> Unit,
    onClickUser: (ListUser) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .safeDrawingPadding()
                .imePadding()
                .fillMaxSize(),
    ) {
        UserSearchInput(
            inputText = uiState.queryText,
            onValueChange = onUpdateQuery,
        )
        when (uiState) {
            is UserSearchUiState.Empty -> {
                UserSearchContentEmpty()
            }
            is UserSearchUiState.ContentIsReady -> {
                UserSearchContent(
                    userList = uiState.userListResult,
                    onClickUser = onClickUser,
                )
            }
        }
    }
}
