package com.kaeritei.githubclient.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.kaeritei.githubclient.R
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ErrorDialogEffect(errorFlow: SharedFlow<Throwable>) {
    var errorState by remember { mutableStateOf<Throwable?>(null) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            errorFlow.collect { error ->
                errorState = error
            }
        }
    }

    if (errorState != null) {
        ErrorDialog(
            error = errorState,
            onDismissRequest = {
                errorState = null
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorDialog(
    error: Throwable?,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (error == null) {
        return
    }
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = error.message ?: stringResource(id = R.string.unknown_error_message),
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = onDismissRequest,
                ) {
                    Text(
                        text = stringResource(id = R.string.ok_button_label),
                    )
                }
            }
        }
    }
}
