package com.app.thingscrossing.feature_account.presentation.sign_in

sealed interface SignInEvent {
    class PasswordChange(val password: String) : SignInEvent
    class UsernameChange(val username: String) : SignInEvent

    object ChangePasswordVisibility : SignInEvent
}