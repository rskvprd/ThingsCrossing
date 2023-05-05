package com.app.thingscrossing.feature_account.presentation.util

sealed class AccountScreen(val route: String) {
    object RegistrationScreen : AccountScreen("registration")
    object LoginScreen : AccountScreen("login")
}