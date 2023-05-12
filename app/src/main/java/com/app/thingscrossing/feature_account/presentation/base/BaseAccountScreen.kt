package com.app.thingscrossing.feature_account.presentation.base

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.feature_account.presentation.profile.ProfileScreen
import com.app.thingscrossing.feature_account.presentation.registration.RegistrationScreen
import com.app.thingscrossing.feature_account.presentation.sign_in.SignInScreen
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.LoadingDialog

@Composable
fun BaseAccountScreen(
    navController: NavHostController,
    viewModel: BaseAccountViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    if (uiState.isLoading) {
        LoadingDialog(progression = null)
        return
    }
    if (uiState.errorMessageId != null) {
        ErrorDialog(
            onDismissError = {
                viewModel.onEvent(BaseAccountEvent.DismissError)
            },
            errorMessageId = uiState.errorMessageId
        )
    }
    if (uiState.authKey == null) {
        if (uiState.haveAccount) {
            SignInScreen(
                navController = navController,
                onChangeHaveAccount = { viewModel.onEvent(BaseAccountEvent.ChangeHaveAccount) },
                onSignIn = { user -> viewModel.onEvent(BaseAccountEvent.SignIn(user)) }
            )
        } else {
            RegistrationScreen(
                navController = navController,
                onChangeHaveAccount = { viewModel.onEvent(BaseAccountEvent.ChangeHaveAccount) },
                onSignUp = { user -> viewModel.onEvent(BaseAccountEvent.SignUp(user)) }
            )
        }
    } else {
        ProfileScreen(
            navController = navController
        ) { viewModel.onEvent(BaseAccountEvent.SignOut) }
    }
}