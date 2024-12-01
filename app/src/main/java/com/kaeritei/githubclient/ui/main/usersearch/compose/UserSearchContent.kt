package com.kaeritei.githubclient.ui.main.usersearch.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaeritei.githubclient.ui.component.RoundUserIcon
import com.kaeritei.githubclient.ui.main.usersearch.ListUser

@Composable
fun UserSearchContent(
    userList: List<ListUser>,
    onClickUser: (ListUser) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(userList) { user ->
            UserListItem(
                user = user,
                onClick = { onClickUser(user) },
            )
        }
    }
}

@Composable
private fun UserListItem(
    user: ListUser,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .clickable { onClick() }
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RoundUserIcon(
            imageUrl = user.thumbnailUrl,
            modifier = Modifier.size(40.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = user.userName,
            style = MaterialTheme.typography.labelLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserListItemPreview() {
    UserListItem(
        user =
            ListUser(
                userName = "octocat",
                thumbnailUrl = "",
            ),
        onClick = {},
    )
}
