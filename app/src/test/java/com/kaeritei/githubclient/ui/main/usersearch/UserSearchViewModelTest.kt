package com.kaeritei.githubclient.ui.main.usersearch

import com.kaeritei.githubclient.data.api.ApiClientWrapper
import com.kaeritei.githubclient.testing.rule.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.mockito.kotlin.mock

@Suppress("NonAsciiCharacters", "RemoveRedundantBackticks")
class UserSearchViewModelTest {
    private val mainDispatcherRule = MainDispatcherRule()
    private lateinit var apiClientWrapper: ApiClientWrapper
    private lateinit var viewModel: UserSearchViewModel

    @get:Rule
    val ruleChain: RuleChain =
        RuleChain
            .outerRule(mainDispatcherRule)

    @Before
    fun setup() {
        apiClientWrapper = mock()
        viewModel = UserSearchViewModel(apiClientWrapper)
    }

    @Test
    fun `初期状態のuiStateがUserSearchUiState Emptyになっている`() {
        assert(viewModel.uiState.value is UserSearchUiState.Empty)
    }

    @Test
    fun `updateQueryText_入力したクエリがuiStateに流れる`() =
        runTest {
            val query = "test"
            viewModel.updateQueryText(query).join()
            assert((viewModel.uiState.value.queryText == query))
        }
}
