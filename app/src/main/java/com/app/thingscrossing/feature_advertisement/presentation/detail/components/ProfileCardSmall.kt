package com.app.thingscrossing.feature_advertisement.presentation.detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.data.remote.dto.UserProfile

@Composable
fun ProfileCardSmall(
    userProfile: UserProfile
) {
    Row (verticalAlignment = Alignment.CenterVertically){
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            model = userProfile.avatar,
            contentDescription = stringResource(id = R.string.user_profile_picture_desc)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = userProfile.user.username)
    }
}