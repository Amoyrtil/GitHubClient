package com.kaeritei.githubclient.ui.main.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaeritei.api.models.UserDetailResponse
import com.kaeritei.githubclient.data.api.ApiClientWrapper
import com.kaeritei.githubclient.data.api.successOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UserDetailUiState {
    data object Loading : UserDetailUiState

    data class ContentIsReady(
        val userDetail: UserDetail,
        val repositories: List<ListRepository>,
    ) : UserDetailUiState
}

private data class UserDetailViewModelState(
    val userDetail: UserDetail? = null,
    val repositories: List<ListRepository>? = null,
) {
    val toUiState: UserDetailUiState =
        if (userDetail != null && repositories != null) {
            UserDetailUiState.ContentIsReady(userDetail, repositories)
        } else {
            UserDetailUiState.Loading
        }
}

@HiltViewModel
class UserDetailViewModel
    @Inject
    constructor(
        private val apiClientWrapper: ApiClientWrapper,
    ) : ViewModel() {
        private val _systemErrorFlow = MutableSharedFlow<Throwable>()
        val systemErrorFlow: SharedFlow<Throwable> = _systemErrorFlow

        private val exceptionHandler =
            CoroutineExceptionHandler { _, exception ->
                viewModelScope.launch {
                    _systemErrorFlow.emit(exception)
                }
            }

        private val viewModelState = MutableStateFlow(UserDetailViewModelState())
        val uiState: StateFlow<UserDetailUiState> =
            viewModelState
                .map { it.toUiState }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Eagerly,
                    viewModelState.value.toUiState,
                )

        private val _transitionFlow = MutableSharedFlow<UserDetailDestination>()
        val transitionFlow: SharedFlow<UserDetailDestination> = _transitionFlow

        @OptIn(ExperimentalCoroutinesApi::class)
        fun refreshUserData(userName: String) =
            viewModelScope.launch(exceptionHandler) {
                viewModelState.update {
                    UserDetailViewModelState()
                }

                val deferredUserDetail =
                    async {
                        apiClientWrapper.getUserDetail(userName).successOrThrow()
                    }
                val deferredRepositories =
                    async {
                        apiClientWrapper.getUserRepositories(userName).successOrThrow()
                    }
                awaitAll(
                    deferredUserDetail,
                    deferredRepositories,
                )
                val userDetailResponse = deferredUserDetail.getCompleted()
                val repositoriesResponse = deferredRepositories.getCompleted()

                viewModelState.update {
                    UserDetailViewModelState(
                        userDetail = userDetailResponse.asUserDetail(),
                        repositories = repositoriesResponse.map { it.toUiCompatible() },
                    )
                }
            }

        fun transitionTo(destination: UserDetailDestination) {
            viewModelScope.launch {
                _transitionFlow.emit(destination)
            }
        }

        private fun UserDetailResponse.asUserDetail(): UserDetail =
            UserDetail(
                thumbnailUrl = avatarUrl,
                userName = login,
                fullName = name,
                followersCount = followers,
                followingCount = following,
            )

        private fun com.kaeritei.api.models.ListRepository.toUiCompatible(): ListRepository =
            ListRepository(
                name = name,
                description = description ?: "",
                language = language ?: "",
                starCount = stargazersCount,
                url = htmlUrl,
            )
    }
