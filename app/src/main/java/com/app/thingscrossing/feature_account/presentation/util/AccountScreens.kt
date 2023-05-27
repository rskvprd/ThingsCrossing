package com.app.thingscrossing.feature_account.presentation.util

sealed class AccountScreens(val route: String) {
    object RegistrationScreens : AccountScreens("registration")
    object LoginScreens : AccountScreens("login")
}