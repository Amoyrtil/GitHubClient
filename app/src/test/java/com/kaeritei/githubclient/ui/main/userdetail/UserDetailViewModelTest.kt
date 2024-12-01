package com.kaeritei.githubclient.ui.main.userdetail

import androidx.lifecycle.viewModelScope
import com.kaeritei.api.models.ListRepository
import com.kaeritei.api.models.UserDetailResponse
import com.kaeritei.githubclient.data.api.ApiClientWrapper
import com.kaeritei.githubclient.data.api.Result
import com.kaeritei.githubclient.testing.rule.MainDispatcherRule
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@Suppress("NonAsciiCharacters", "RemoveRedundantBackticks")
class UserDetailViewModelTest {
    private val mainDispatcherRule = MainDispatcherRule()
    private lateinit var apiClientWrapper: ApiClientWrapper
    private lateinit var viewModel: UserDetailViewModel

    @get:Rule
    val ruleChain: RuleChain =
        RuleChain
            .outerRule(mainDispatcherRule)

    @Before
    fun setup() {
        apiClientWrapper = mock()
        viewModel = UserDetailViewModel(apiClientWrapper)
    }

    @Test
    fun `初期状態のuiStateがUserDetailUiState Loadingになっている`() {
        assert(viewModel.uiState.value is UserDetailUiState.Loading)
    }

    @Test
    fun `transitionTo_transitionFlowに指定したdestinationが流れる`() =
        runTest {
            val transitionFlow =
                viewModel.transitionFlow
                    .stateIn(
                        viewModel.viewModelScope,
                        SharingStarted.Eagerly,
                        Unit,
                    )
            listOf(
                UserDetailDestination.OpenBrowser(
                    url = "mockUrl",
                ),
            ).forEach { destination ->
                viewModel.transitionTo(destination)
                assert(transitionFlow.value == destination)
            }
        }

    @Test
    fun `fetchUserDetail_ユーザー情報が取得できる`() =
        runTest {
            doReturn(
                Result.Success(
                    getMockUserDetailResponse(),
                ),
            ).whenever(apiClientWrapper).getUserDetail(any())
            doReturn(
                Result.Success(
                    emptyList<ListRepository>(),
                ),
            ).whenever(apiClientWrapper).getUserRepositories(any())

            viewModel.refreshUserData("mockUserName").join()

            assert(viewModel.uiState.value is UserDetailUiState.ContentIsReady)
            val uistateContentIsReady = viewModel.uiState.value as UserDetailUiState.ContentIsReady
            assert(uistateContentIsReady.userDetail == getMockUserDetail())
            assert(uistateContentIsReady.repositories.isEmpty())
        }

    private fun getMockUserDetailResponse(): UserDetailResponse {
        return UserDetailResponse(
            avatarUrl = "mockAvatarUrl",
            login = "mockLogin",
            name = "mockName",
            followers = 100,
            following = 200,
        )
    }

    private fun getMockUserDetail(): UserDetail {
        return UserDetail(
            thumbnailUrl = "mockAvatarUrl",
            userName = "mockLogin",
            fullName = "mockName",
            followersCount = 100,
            followingCount = 200,
        )
    }
}
