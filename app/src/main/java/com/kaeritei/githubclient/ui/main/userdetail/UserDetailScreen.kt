package com.kaeritei.githubclient.ui.main.userdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaeritei.githubclient.ui.component.LoadingContent
import com.kaeritei.githubclient.ui.main.MainNavRoute

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel,
    navRoute: MainNavRoute.UserDetail,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userName = navRoute.userName

    LaunchedEffect(userName) {
        viewModel.fetchUserDetail(
            userName = userName,
        )
    }

    UserDetailScreen(uiState)
}

@Composable
private fun UserDetailScreen(uiState: UserDetailUiState) {
    when (uiState) {
        is UserDetailUiState.Loading -> {
            LoadingContent()
        }
        is UserDetailUiState.ContentIsReady -> {
            // ContentIsReady
        }
    }
}
