package com.kaeritei.githubclient.ui.main.userdetail.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaeritei.githubclient.R
import com.kaeritei.githubclient.ui.common.NestedScrollConnections
import com.kaeritei.githubclient.ui.component.RoundUserIcon
import com.kaeritei.githubclient.ui.main.userdetail.ListRepository
import com.kaeritei.githubclient.ui.main.userdetail.UserDetail

@Composable
fun UserDetailContent(
    userDetail: UserDetail,
    repositories: List<ListRepository>,
    onClickRepository: (ListRepository) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var wrapperHeightDp by remember { mutableFloatStateOf(0f) }
    var lazyListHeaderHeightDp by remember { mutableFloatStateOf(0f) }
    val lazyColumnHeightDp = wrapperHeightDp - lazyListHeaderHeightDp
    val wrapperScrollState = rememberScrollState()
    val lazyListStates = rememberLazyListState()
    val nestedScrollConnection =
        NestedScrollConnections.hideFixedHeaderContent(
            wrapperScrollableState = wrapperScrollState,
            contentLazyListState = lazyListStates,
        )

    Column(
        modifier =
            modifier.fillMaxSize()
                .onGloballyPositioned {
                    with(density) {
                        wrapperHeightDp = it.size.height.toDp().value
                    }
                }
                .nestedScroll(nestedScrollConnection)
                .verticalScroll(wrapperScrollState),
    ) {
        ProfileContent(
            userDetail = userDetail,
        )
        if (repositories.isNotEmpty()) {
            RepositoryHeader(
                modifier =
                    Modifier.onGloballyPositioned {
                        with(density) {
                            lazyListHeaderHeightDp = it.size.height.toDp().value
                        }
                    },
            )
            RepositoryContent(
                modifier =
                    Modifier.heightIn(
                        min = 0.dp,
                        max = lazyColumnHeightDp.dp,
                    ),
                repositories = repositories,
                listState = lazyListStates,
                onClickItem = onClickRepository,
            )
        }
    }
}

@Composable
private fun ProfileContent(userDetail: UserDetail) {
    Row(
        modifier =
            Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                )
                .fillMaxWidth(),
    ) {
        RoundUserIcon(
            modifier = Modifier.size(80.dp),
            imageUrl = userDetail.thumbnailUrl,
        )
        Spacer(modifier = Modifier.size(16.dp))
        ProfileInfo(
            userDetail = userDetail,
        )
    }
}

@Composable
private fun ProfileInfo(userDetail: UserDetail) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (userDetail.fullName.isNullOrEmpty()) {
            ProfileUserName(
                userName = userDetail.userName,
                isUserNameOnly = true,
            )
        } else {
            ProfileNames(
                fullName = userDetail.fullName,
                userName = userDetail.userName,
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        ProfileInfoFollow(
            followersCount = userDetail.followersCount,
            followingCount = userDetail.followingCount,
        )
    }
}

@Composable
private fun ProfileNames(
    fullName: String,
    userName: String,
) {
    Column {
        Text(
            text = fullName,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        ProfileUserName(
            userName = userName,
        )
    }
}

@Composable
private fun ProfileUserName(
    userName: String,
    isUserNameOnly: Boolean = false,
) {
    Text(
        text = "@$userName",
        style =
            if (isUserNameOnly) {
                MaterialTheme.typography.titleLarge
            } else {
                MaterialTheme.typography.labelLarge
            },
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
private fun ProfileInfoFollow(
    followersCount: Int,
    followingCount: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FollowLabel(
            labelResId = R.string.follow_label,
            count = followingCount,
        )
        Spacer(modifier = Modifier.size(8.dp))
        FollowLabel(
            labelResId = R.string.followers_label,
            count = followersCount,
        )
    }
}

@Composable
private fun FollowLabel(
    @StringRes labelResId: Int,
    count: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(labelResId),
            style = MaterialTheme.typography.labelMedium,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun RepositoryContent(
    repositories: List<ListRepository>,
    listState: LazyListState,
    onClickItem: (ListRepository) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
    ) {
        items(repositories) { repository ->
            RepositoryItem(
                repository = repository,
                onClick = { onClickItem(repository) },
            )
            HorizontalDivider()
        }
    }
}

@Composable
private fun RepositoryHeader(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.repository_header),
        style = MaterialTheme.typography.titleMedium,
        modifier =
            modifier.padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
            ),
    )
}

@Composable
private fun RepositoryItem(
    repository: ListRepository,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .clickable { onClick() }
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = repository.name,
            style = MaterialTheme.typography.titleSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        if (repository.description.isNotEmpty()) {
            Text(
                text = repository.description,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (repository.language.isNotEmpty()) {
                RepositoryLabelWithIcon(
                    imageVector = Icons.Default.Create,
                    label = repository.language,
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
            RepositoryLabelWithIcon(
                imageVector = Icons.Default.Star,
                label = repository.starCount.toString(),
            )
        }
    }
}

@Composable
private fun RepositoryLabelWithIcon(
    imageVector: ImageVector,
    label: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = imageVector,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileContentPreview() {
    ProfileContent(
        userDetail =
            UserDetail(
                thumbnailUrl = "",
                fullName = "John Doe",
                userName = "johndoe",
                followersCount = 100,
                followingCount = 200,
            ),
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileContentWithoutFullNamePreview() {
    ProfileContent(
        userDetail =
            UserDetail(
                thumbnailUrl = "",
                fullName = null,
                userName = "johndoe",
                followersCount = 100,
                followingCount = 200,
            ),
    )
}

@Preview(showBackground = true)
@Composable
private fun RepositoryItemPreview() {
    RepositoryItem(
        repository =
            ListRepository(
                name = "Repository Name",
                description = "Description",
                language = "Kotlin",
                starCount = 100,
                url = "https://example.com",
            ),
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun RepositoryItemWithoutLanguagePreview() {
    RepositoryItem(
        repository =
            ListRepository(
                name = "Repository Name",
                description = "Description",
                language = "",
                starCount = 100,
                url = "https://example.com",
            ),
        onClick = {},
    )
}
