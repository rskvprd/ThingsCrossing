package com.app.thingscrossing.feature_account.presentation.profile

sealed interface ProfileEvent {
    object SignOut: ProfileEvent
}