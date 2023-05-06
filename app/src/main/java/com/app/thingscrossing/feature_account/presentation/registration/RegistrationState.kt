package com.app.thingscrossing.feature_account.presentation.registration

import androidx.annotation.StringRes

data class RegistrationState(
    val isLoading: Boolean = false,
    val authKey: String? = null,

    val username: String = "",
    val email: String = "",
    val password: String = "",
    val secondPassword: String = "",

    val isPasswordValid: Boolean = true,
    val isUsernameValid: Boolean = true,
    val isSecondPasswordValid: Boolean = true,
    val isEmailValid: Boolean = true,

    val isPasswordVisible: Boolean = false,
    val isSecondPasswordVisible: Boolean = false,

    val registrationAvailable: Boolean = false,
    @StringRes val errorMessageId: Int? = null,
)