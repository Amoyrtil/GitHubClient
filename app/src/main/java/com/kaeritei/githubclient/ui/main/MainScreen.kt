package com.kaeritei.githubclient.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kaeritei.githubclient.ui.main.userdetail.UserDetailScreen
import com.kaeritei.githubclient.ui.main.userdetail.UserDetailViewModel
import com.kaeritei.githubclient.ui.main.usersearch.UserSearchScreen
import com.kaeritei.githubclient.ui.main.usersearch.UserSearchViewModel

@Composable
fun MainScreen(
    userSearchViewModel: UserSearchViewModel,
    userDetailViewModel: UserDetailViewModel,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainNavRoute.UserSearch,
    ) {
        composable<MainNavRoute.UserSearch> {
            UserSearchScreen(
                viewModel = userSearchViewModel,
                onClickUser = {
                    navController.navigate(
                        MainNavRoute.UserDetail(
                            userName = it.userName,
                        ),
                    )
                },
            )
        }
        composable<MainNavRoute.UserDetail> { backStackEntry ->
            val navRoute = backStackEntry.toRoute<MainNavRoute.UserDetail>()
            UserDetailScreen(
                viewModel = userDetailViewModel,
                navRoute = navRoute,
            )
        }
    }
}
