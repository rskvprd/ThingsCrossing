package com.app.thingscrossing.feature_account.presentation.profile.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.app.thingscrossing.feature_account.domain.model.UserProfile

@Composable
fun Profile(
    userProfile: UserProfile
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileAvatar(userProfile.avatar)
        ProfileInfo(userProfile.user)
    }
}