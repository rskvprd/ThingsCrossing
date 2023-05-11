package com.app.thingscrossing.feature_account.presentation.base

import androidx.annotation.StringRes

data class BaseAccountState(
    val isLoading: Boolean = true,
    val authKey: String? = null,
    @StringRes val errorMessageId: Int? = null,
)