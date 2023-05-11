package com.app.thingscrossing.feature_account.presentation.registration

import androidx.annotation.StringRes

data class RegistrationState(
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

    val registrationAvailable: Boolean = false,
    @StringRes val errorMessageId: Int? = null,
)


fun RegistrationState.isValid(): Boolean {
    return listOf(
        email.isValidEmail(),
        username.isValidUsername(),
        firstName.isValidFirstName(),
        lastName.isValidLastName(),
        password.isValidPassword(),
        password == secondPassword
    ).all { it }
}

fun String.isValidPassword(): Boolean {
    return this.length >= 8
}

fun String.isValidUsername(): Boolean {
    return this.matches("^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])".toRegex())
}

fun String.isValidFirstName(): Boolean {
    return this.matches("([a-zA-Z\\u0401\\u0451\\u0410-\\u044f]{1,10})\\w+".toRegex())
}

fun String.isValidLastName(): Boolean {
    return this.matches("([a-zA-Z\\u0401\\u0451\\u0410-\\u044f]{1,10})\\w+".toRegex())
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return this.matches(emailRegex.toRegex())
}