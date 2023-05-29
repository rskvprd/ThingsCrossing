package com.app.thingscrossing.feature_account.presentation.sign_up

import androidx.annotation.StringRes
import com.app.thingscrossing.feature_account.presentation.util.isValidEmail
import com.app.thingscrossing.feature_account.presentation.util.isValidFirstName
import com.app.thingscrossing.feature_account.presentation.util.isValidLastName
import com.app.thingscrossing.feature_account.presentation.util.isValidPassword
import com.app.thingscrossing.feature_account.presentation.util.isValidUsername

data class SignUpState(
    val isLoading: Boolean = false,
    val authKey: String? = null,

    val username: String = "",
    val email: String = "",
    val password: String = "",
    val secondPassword: String = "",
    val firstName: String = "",
    val lastName: String = "",

    val isPasswordValid: Boolean = true,
    val isUsernameValid: Boolean = true,
    val isFirstNameValid: Boolean = true,
    val isLastNameValid: Boolean = true,
    val isSecondPasswordValid: Boolean = true,
    val isEmailValid: Boolean = true,

    val isPasswordVisible: Boolean = false,
    val isSecondPasswordVisible: Boolean = false,

    @StringRes val errorMessageId: Int? = null,
)

fun SignUpState.isValid(): Boolean {
    return listOf(
        email.isValidEmail(),
        username.isValidUsername(),
        firstName.isValidFirstName(),
        lastName.isValidLastName(),
        password.isValidPassword(),
        password == secondPassword
    ).all { it }
}

