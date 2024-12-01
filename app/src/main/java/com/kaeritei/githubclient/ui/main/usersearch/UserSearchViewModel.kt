package com.kaeritei.githubclient.ui.main.usersearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaeritei.githubclient.data.api.ApiClientWrapper
import com.kaeritei.githubclient.data.api.payload.UserSearchPayload
import com.kaeritei.githubclient.data.api.successOrThrow
import com.kaeritei.githubclient.ui.main.userdetail.UserDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UserSearchUiState {
    val queryText: String

    data class Empty(
        override val queryText: String,
    ) : UserSearchUiState

    data class ContentIsReady(
        val userListResult: List<ListUser>,
        override val queryText: String,
    ) : UserSearchUiState
}

@HiltViewModel
class UserSearchViewModel
    @Inject
    constructor(
        private val apiClientWrapper: ApiClientWrapper,
    ) : ViewModel() {
        companion object {
            private val TAG = UserDetailViewModel::class.java.simpleName
        }

        private val _systemErrorFlow = MutableSharedFlow<Throwable>()
        val systemErrorFlow: SharedFlow<Throwable> = _systemErrorFlow

        private val exceptionHandler =
            CoroutineExceptionHandler { _, exception ->
                viewModelScope.launch {
                    _systemErrorFlow.emit(exception)
                }
            }

        private val userListState = MutableStateFlow(emptyList<ListUser>())
        private val queryTextState = MutableStateFlow("")

        val uiState =
            combine(
                userListState,
                queryTextState,
            ) { userList, queryText ->
                generateUiState(userList, queryText)
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                generateUiState(userListState.value, queryTextState.value),
            )

        init {
            observeQueryUpdate()
        }

        fun updateQueryText(query: String) =
            viewModelScope.launch(exceptionHandler) {
                queryTextState.emit(query)
            }

        @OptIn(FlowPreview::class)
        private fun observeQueryUpdate() =
            viewModelScope.launch(exceptionHandler) {
                queryTextState
                    .debounce(300)
                    .distinctUntilChanged()
                    .collect {
                        if (it.isEmpty()) {
                            resetSearchResult()
                        } else {
                            searchUser(it)
                        }
                    }
            }

        private suspend fun resetSearchResult() {
            userListState.emit(emptyList())
            queryTextState.emit("")
        }

        private suspend fun searchUser(query: String) {
            val payload =
                UserSearchPayload(
                    query = query,
                    target = UserSearchPayload.SearchTargetType.User,
                )
            val response = apiClientWrapper.getUserQuerySearch(payload).successOrThrow()
            val userListResult = response.items.map { it.toUiCompatible() }

            userListState.emit(userListResult)
        }

        private fun generateUiState(
            userList: List<ListUser>,
            queryText: String,
        ): UserSearchUiState {
            return if (userList.isEmpty()) {
                UserSearchUiState.Empty(
                    queryText = queryText,
                )
            } else {
                UserSearchUiState.ContentIsReady(
                    userListResult = userList,
                    queryText = queryText,
                )
            }
        }

        private fun com.kaeritei.api.models.ListUser.toUiCompatible(): ListUser {
            return ListUser(
                thumbnailUrl = avatarUrl,
                userName = login,
            )
        }
    }
