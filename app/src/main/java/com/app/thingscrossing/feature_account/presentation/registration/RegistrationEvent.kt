package com.app.thingscrossing.feature_account.presentation.registration

sealed interface RegistrationEvent {
    class SaveAuthKey(val key: String) : RegistrationEvent
    class LoginChange(val username: String) : RegistrationEvent
    class PasswordChange(val password: String) : RegistrationEvent
    class EmailChange(val email: String) : RegistrationEvent
    class SecondPasswordChange(val secondPassword: String) : RegistrationEvent
    object SignUp: RegistrationEvent
    object ToggleShowPassword: RegistrationEvent
    object ToggleShowSecondPassword: RegistrationEvent
}