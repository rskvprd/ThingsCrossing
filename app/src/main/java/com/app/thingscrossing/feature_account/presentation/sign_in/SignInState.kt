package com.app.thingscrossing.feature_account.presentation.sign_in

import androidx.annotation.StringRes
import com.app.thingscrossing.feature_account.presentation.util.isValidPassword
import com.app.thingscrossing.feature_account.presentation.util.isValidUsername

data class SignInState(
    val isLoading: Boolean = false,

    val username: String = "",
    val password: String = "",

    val isUsernameValid: Boolean = true,
    val isPasswordValid: Boolean = true,

    val isPasswordVisible: Boolean = false,
    val isSignInAvailable: Boolean = false,
    @StringRes val errorMessageId: Int? = null,
)

fun SignInState.isValid() : Boolean {
    return username.isValidUsername() && password.isValidPassword()
}