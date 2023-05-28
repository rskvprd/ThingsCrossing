package com.app.thingscrossing.feature_chat.presentation.private_chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.domain.model.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanionProfileTopBar(profile: UserProfile) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = profile.avatar,
                    contentDescription = stringResource(id = R.string.user_profile_picture_desc),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "${profile.user.firstName} ${profile.user.lastName}")
            }
        }
    )
}