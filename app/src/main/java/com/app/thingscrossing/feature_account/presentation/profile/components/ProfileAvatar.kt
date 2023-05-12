package com.app.thingscrossing.feature_account.presentation.profile.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.thingscrossing.R

@Composable
fun ProfileAvatar(
    imageUrl: String,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(id = R.string.avatar_image_cont_desc),
        modifier = Modifier
            .clip(CircleShape)
            .size(300.dp)
    )
}