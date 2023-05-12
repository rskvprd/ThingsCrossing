package com.app.thingscrossing.feature_account.presentation.profile

import androidx.annotation.StringRes
import com.app.thingscrossing.feature_account.data.remote.dto.UserProfile

data class ProfileState(
    val isLoading: Boolean = false,
    val authKey: String? = null,
    val login: String = "",
    val password: String = "",
    val currentProfile: UserProfile? = null,
    @StringRes val errorMessageId: Int? = null,
)