package com.app.thingscrossing.feature_account.presentation.profile

import androidx.annotation.StringRes

data class ProfileState(
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null,
)