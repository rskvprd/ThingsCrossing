package com.app.thingscrossing.feature_account.presentation.sign_up

sealed interface SignUpEvent {
    class SaveAuthKey(val key: String) : SignUpEvent

    class UsernameChange(val username: String) : SignUpEvent

    class FirstNameChange(val firstName: String) : SignUpEvent

    class LastNameChange(val lastName: String) : SignUpEvent

    class PasswordChange(val password: String) : SignUpEvent

    class EmailChange(val email: String) : SignUpEvent

    class SecondPasswordChange(val secondPassword: String) : SignUpEvent

    object ToggleShowPassword: SignUpEvent

    object ToggleShowSecondPassword: SignUpEvent

    object SignUp: SignUpEvent

    object ToSignInScreen: SignUpEvent

    object DismissError: SignUpEvent
}

sealed interface SignUpViewModelEvent {
    class Navigate(val route: String): SignUpViewModelEvent
}