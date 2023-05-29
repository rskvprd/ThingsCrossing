package com.app.thingscrossing.feature_account.presentation.profile

import android.net.Uri
import androidx.annotation.StringRes
import com.app.thingscrossing.feature_account.domain.model.UserProfile

data class ProfileState(
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null,
    val showAddImageDialog: Boolean = false,
    val currentImageUri: Uri? = null,
    val uploadingAvatarProgress: Float? = null,
)