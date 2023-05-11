package com.app.thingscrossing.feature_account.presentation.base

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.feature_account.presentation.profile.ProfileScreen
import com.app.thingscrossing.feature_account.presentation.registration.RegistrationScreen
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.LoadingDialog
import com.app.thingscrossing.feature_advertisement.presentation.search.NetworkErrorMessage

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
        NetworkErrorMessage(messageId = uiState.errorMessageId)
        return
    }
    if (uiState.authKey == null) {
        RegistrationScreen(navController = navController)
    } else {
        ProfileScreen(
            navController = navController,
            authKey = uiState.authKey,
            onSignOut = { viewModel.onEvent(BaseAccountEvent.SignOut) }
        )
    }
}