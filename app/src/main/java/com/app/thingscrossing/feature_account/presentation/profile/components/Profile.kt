package com.app.thingscrossing.feature_account.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_account.presentation.profile.ProfileEvent
import com.app.thingscrossing.feature_account.presentation.profile.ProfileState

@Composable
fun Profile(
    userProfile: UserProfile,
    uiState: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileAvatar(
            uiState = uiState,
            imageUrl = userProfile.avatar,
            onPickImage = { uri -> onEvent(ProfileEvent.PickImage(uri!!)) },
            onConfirmImage = {
                onEvent(ProfileEvent.UploadImage(uiState.currentImageUri!!))
            },
            onAddImageClick = {
                onEvent(ProfileEvent.AddImageClick)
            },
            onDropImage = {
                onEvent(ProfileEvent.DropImage)
            },
            dismissAddImageDialog = {
                onEvent(ProfileEvent.DismissAddImageDialog)
            }
        )
        ProfileInfo(userProfile.user)
    }
}