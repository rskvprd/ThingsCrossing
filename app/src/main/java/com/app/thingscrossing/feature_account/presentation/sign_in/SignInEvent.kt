package com.app.thingscrossing.feature_account.presentation.sign_in

sealed interface SignInEvent {
    class PasswordChange(val password: String) : SignInEvent

    class UsernameChange(val username: String) : SignInEvent

    object ChangePasswordVisibility : SignInEvent

    object SignIn : SignInEvent

    object ToSignUpScreen: SignInEvent

    object DismissError: SignInEvent
}

sealed interface SignInViewModelEvent {
    class Navigate(val route: String) : SignInViewModelEvent
}