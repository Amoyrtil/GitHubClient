package com.kaeritei.githubclient.ui.main.userdetail

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaeritei.githubclient.ui.component.LoadingContent
import com.kaeritei.githubclient.ui.main.MainNavRoute
import com.kaeritei.githubclient.ui.main.userdetail.compose.UserDetailContent

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel,
    navRoute: MainNavRoute.UserDetail,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userName = navRoute.userName

    LaunchedEffect(userName) {
        viewModel.refreshUserData(
            userName = userName,
        )
    }

    UserDetailScreen(
        uiState = uiState,
        onClickRepository = { repository ->
            viewModel.transitionTo(UserDetailDestination.OpenBrowser(repository.url))
        },
    )
}

@Composable
private fun UserDetailScreen(
    uiState: UserDetailUiState,
    onClickRepository: (ListRepository) -> Unit,
) {
    when (uiState) {
        is UserDetailUiState.Loading -> {
            LoadingContent()
        }
        is UserDetailUiState.ContentIsReady -> {
            UserDetailContent(
                modifier = Modifier.safeDrawingPadding(),
                userDetail = uiState.userDetail,
                repositories = uiState.repositories,
                onClickRepository = onClickRepository,
            )
        }
    }
}
