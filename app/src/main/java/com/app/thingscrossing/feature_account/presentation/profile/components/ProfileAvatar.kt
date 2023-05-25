package com.app.thingscrossing.feature_account.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    Box {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(id = R.string.avatar_image_cont_desc),
            modifier = Modifier
                .clip(CircleShape)
                .size(130.dp)
        )
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
        ) {
            Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = null)
        }
    }

}