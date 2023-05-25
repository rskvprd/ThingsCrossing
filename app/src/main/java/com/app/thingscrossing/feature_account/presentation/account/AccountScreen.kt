package com.app.thingscrossing.feature_account.presentation.account

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.app.thingscrossing.feature_account.presentation.profile.ProfileScreen
import com.app.thingscrossing.feature_account.presentation.registration.RegistrationScreen
import com.app.thingscrossing.feature_account.presentation.sign_in.SignInScreen
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.LoadingDialog

@Composable
fun AccountScreen(
    navController: NavHostController,
    uiState: AccountState,
    onEvent: (AccountEvent) -> Unit,
) {

    if (uiState.isLoading) {
        LoadingDialog(progression = null)
        return
    }
    if (uiState.errorMessageId != null) {
        ErrorDialog(
            onDismissError = {
                onEvent(AccountEvent.DismissError)
            },
            errorMessageId = uiState.errorMessageId
        )
    }
    if (uiState.authKey == null) {
        if (uiState.haveAccount) {
            SignInScreen(
                navController = navController,
                onChangeHaveAccount = { onEvent(AccountEvent.ChangeHaveAccount) },
                onSignIn = { user -> onEvent(AccountEvent.SignIn(user)) }
            )
        } else {
            RegistrationScreen(
                navController = navController,
                onChangeHaveAccount = { onEvent(AccountEvent.ChangeHaveAccount) },
                onSignUp = { user -> onEvent(AccountEvent.SignUp(user)) }
            )
        }
    } else {
        ProfileScreen(
            navController = navController,
            currentUserProfile = uiState.currentUserProfile,
            onSignOut = { onEvent(AccountEvent.SignOut) }
        )
    }
}