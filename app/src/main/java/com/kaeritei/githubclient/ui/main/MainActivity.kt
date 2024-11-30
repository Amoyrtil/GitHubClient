package com.kaeritei.githubclient.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.kaeritei.githubclient.misc.UrlHandler
import com.kaeritei.githubclient.ui.component.GitHubClientTheme
import com.kaeritei.githubclient.ui.main.userdetail.UserDetailDestination
import com.kaeritei.githubclient.ui.main.userdetail.UserDetailViewModel
import com.kaeritei.githubclient.ui.main.usersearch.UserSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var urlHandler: UrlHandler

    private val userSearchViewModel: UserSearchViewModel by viewModels()
    private val userDetailViewModel: UserDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleUserDetailTransitionFlow()

        enableEdgeToEdge()
        setContent {
            GitHubClientTheme {
                MainScreen(
                    userSearchViewModel = userSearchViewModel,
                    userDetailViewModel = userDetailViewModel,
                )
            }
        }
    }

    private fun handleUserDetailTransitionFlow() {
        lifecycleScope.launch {
            userDetailViewModel.transitionFlow.collect { destination ->
                when (destination) {
                    is UserDetailDestination.OpenBrowser -> {
                        urlHandler.handleBrowseUrl(
                            context = this@MainActivity,
                            url = destination.url,
                        )
                    }
                }
            }
        }
    }
}
