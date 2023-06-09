package com.app.thingscrossing.feature_account.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.ConfirmDialog
import com.app.thingscrossing.feature_account.presentation.profile.components.Profile
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.LoadingDialog
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(key1 = null) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProfileViewModelEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    if (uiState.isLoading) {
        LoadingDialog(progression = null)
    }

    if (uiState.errorMessageId != null) {
        ErrorDialog(
            onDismissError = { viewModel.onEvent(ProfileEvent.DismissError) },
            errorMessageId = uiState.errorMessageId
        )
    }

    if (uiState.showConfirmSignOutDialog) {
        ConfirmDialog(
            onDismiss = { viewModel.onEvent(ProfileEvent.DismissSignOutDialog) },
            onConfirm = {
                viewModel.onEvent(ProfileEvent.SignOut)
                viewModel.onEvent(ProfileEvent.DismissSignOutDialog)
            },
            infoTextId = R.string.confirm_sign_out_dialog_text,
            confirmButtonTextId = R.string.sign_out
        )
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.profile_screen_title)) })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            viewModel.currentUserProfile?.let { profile ->
                Profile(
                    userProfile = profile, uiState = uiState, onEvent = viewModel::onEvent
                )
                Spacer(modifier = Modifier.height(20.dp))
            }


            Button(
                modifier = Modifier.fillMaxWidth(), onClick = {
                    viewModel.onEvent(ProfileEvent.ShowConfirmSignOutDialog)
                }, colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text(
                    text = stringResource(id = R.string.sign_out),
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }


}