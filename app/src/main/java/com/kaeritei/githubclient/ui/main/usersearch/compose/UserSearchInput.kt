package com.kaeritei.githubclient.ui.main.usersearch.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaeritei.githubclient.R

@Composable
fun UserSearchInput(
    inputText: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        value = inputText,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.user_search_input_placeholder)
            )
        },
        trailingIcon = {
            Icon(
                modifier =
                    Modifier.clickable {
                        onValueChange("")
                    },
                imageVector = Icons.Default.Clear,
                contentDescription = null,
            )
        },
    )
}
