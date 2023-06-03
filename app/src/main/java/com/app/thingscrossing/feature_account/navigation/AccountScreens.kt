package com.app.thingscrossing.feature_account.navigation

sealed class AccountScreens(val route: String) {
    object SignUpScreen : AccountScreens("registration-screen")

    object SignInScreen : AccountScreens("login-screen")

    object ProfileScreen: AccountScreens("profile-screen")

    companion object {
        const val ROUTE = "account"
        val ALL_ROUTES = listOf(
            SignUpScreen.route,
            SignInScreen.route,
            ProfileScreen.route,
        )
    }
}