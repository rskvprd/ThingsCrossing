package com.app.thingscrossing.feature_account.presentation.registration

sealed interface RegistrationEvent {
    object GetAuthKey: RegistrationEvent
    class SaveAuthKey(val key: String): RegistrationEvent
    class LoginChange(val login: String): RegistrationEvent
}