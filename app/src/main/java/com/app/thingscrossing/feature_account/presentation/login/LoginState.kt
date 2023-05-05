package com.app.thingscrossing.feature_account.presentation.login

import androidx.annotation.StringRes

data class LoginState(
    val isLoading: Boolean = false,
    val authKey: String? = null,
    val login: String = "",
    val password: String = "",
    @StringRes val errorMessageId: Int? = null,
)