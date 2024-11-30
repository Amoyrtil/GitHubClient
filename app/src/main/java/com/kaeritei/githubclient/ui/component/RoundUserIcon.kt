package com.kaeritei.githubclient.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun RoundUserIcon(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
) {
    val context = LocalContext.current
    Image(
        modifier =
            modifier
                .clip(CircleShape),
        painter =
            rememberAsyncImagePainter(
                model =
                    ImageRequest.Builder(context)
                        .data(imageUrl)
                        .build(),
            ),
        contentDescription = contentDescription,
    )
}
