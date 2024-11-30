package com.kaeritei.githubclient.ui.main.usersearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaeritei.githubclient.data.api.ApiClientWrapper
import com.kaeritei.githubclient.data.api.payload.UserSearchPayload
import com.kaeritei.githubclient.data.api.successOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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

sealed interface UserSearchUiState {
    data object Init : UserSearchUiState

    data class ContentIsReady(
        val userListResult: List<ListUser>,
    ) : UserSearchUiState
}

private data class UserSearchViewModelState(
    val userListResult: List<ListUser>? = null,
) {
    fun toUiState(): UserSearchUiState =
        if (userListResult == null) {
            UserSearchUiState.Init
        } else {
            UserSearchUiState.ContentIsReady(userListResult)
        }
}

@HiltViewModel
class UserSearchViewModel
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

        private val viewModelState = MutableStateFlow(UserSearchViewModelState())
        val uiState: StateFlow<UserSearchUiState> =
            viewModelState
                .map { it.toUiState() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Eagerly,
                    viewModelState.value.toUiState(),
                )

        fun searchUser(query: String) =
            viewModelScope.launch(exceptionHandler) {
                val payload =
                    UserSearchPayload(
                        query = query,
                        target = UserSearchPayload.SearchTargetType.User,
                    )
                val response = apiClientWrapper.getUserQuerySearch(payload).successOrThrow()
                val userListResult = response.items.map { it.toUiCompatible() }

                viewModelState.update { it.copy(userListResult = userListResult) }
            }

        private fun com.kaeritei.api.models.ListUser.toUiCompatible(): ListUser {
            return ListUser(
                thumbnailUrl = avatarUrl,
                userName = login,
            )
        }
    }
