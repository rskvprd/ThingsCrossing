package com.app.thingscrossing.feature_account.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.app.thingscrossing.feature_account.data.remote.dto.UserProfile

@Composable
fun Profile(
    userProfile: UserProfile
) {
    Column {
        ProfileAvatar(userProfile.avatar)
    }
}