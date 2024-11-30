package com.kaeritei.githubclient.ui.main.userdetail.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
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
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ProfileContent(
            userDetail = userDetail,
        )
        RepositoryContent(
            repositories = repositories,
            onClickItem = onClickRepository,
        )
    }
}

@Composable
private fun ProfileContent(userDetail: UserDetail) {
    Row(
        modifier =
            Modifier
                .padding(16.dp)
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
        if (userDetail.fullName != null) {
            ProfileNames(
                fullName = userDetail.fullName,
                userName = userDetail.userName,
            )
        } else {
            ProfileUserNameOnly(
                userName = userDetail.userName,
            )
        }
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
        )
        Spacer(modifier = Modifier.size(4.dp))
        ProfileUserNameOnly(
            userName = userName,
        )
    }
}

@Composable
private fun ProfileUserNameOnly(userName: String) {
    Text(
        text = "@$userName",
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
        Text(
            text = "followers: $followersCount",
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "following: $followingCount",
        )
    }
}

@Composable
private fun RepositoryContent(
    repositories: List<ListRepository>,
    onClickItem: (ListRepository) -> Unit,
) {
    LazyColumn {
        items(repositories) { repository ->
            RepositoryItem(
                repository = repository,
                onClick = { onClickItem(repository) },
            )
        }
    }
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
                .padding(16.dp)
                .fillMaxWidth(),
    ) {
        Text(
            text = repository.name,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = repository.description,
        )
        Row {
            if(repository.language.isNotEmpty()) {
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
    Row {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = imageVector,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = label,
        )
    }
}
