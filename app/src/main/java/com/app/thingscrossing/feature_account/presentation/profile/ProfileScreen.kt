package com.app.thingscrossing.feature_account.presentation.profile

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.presentation.profile.components.Profile
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.Block
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.LoadingDialog

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
    onSignOut: () -> Unit,
) {
    val uiState = viewModel.uiState

    Block(
        title = stringResource(id = R.string.profile_screen_title),
        description = stringResource(id = R.string.profile_screen_description)
    ) {
        if (uiState.isLoading) {
            LoadingDialog(progression = null)
        }

        if (uiState.errorMessageId != null) {
            ErrorDialog(
                onDismissError = { viewModel.onEvent(ProfileEvent.DismissError) },
                errorMessageId = uiState.errorMessageId
            )
        }

        if (uiState.currentProfile != null) {
            Profile(userProfile = uiState.currentProfile)
        }

        Button(onClick = { onSignOut() }) {
            Text(text = stringResource(id = R.string.sign_out))
        }
    }
}