package com.app.thingscrossing.feature_account.presentation.registration

sealed interface RegistrationEvent {
    class SaveAuthKey(val key: String) : RegistrationEvent
    class UsernameChange(val username: String) : RegistrationEvent
    class FirstNameChange(val firstName: String) : RegistrationEvent
    class LastNameChange(val lastName: String) : RegistrationEvent
    class PasswordChange(val password: String) : RegistrationEvent
    class EmailChange(val email: String) : RegistrationEvent
    class SecondPasswordChange(val secondPassword: String) : RegistrationEvent
    object ToggleShowPassword: RegistrationEvent
    object ToggleShowSecondPassword: RegistrationEvent
}

sealed interface RegistrationViewModelEvent {
    class NavigateEvent(val route: String): RegistrationViewModelEvent
}