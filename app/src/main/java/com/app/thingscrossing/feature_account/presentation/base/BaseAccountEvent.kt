package com.app.thingscrossing.feature_account.presentation.base

sealed interface BaseAccountEvent {
    object SignOut: BaseAccountEvent
}