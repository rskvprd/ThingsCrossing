package com.app.thingscrossing.feature_account.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_account.presentation.profile.components.Profile
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.Block
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.LoadingDialog

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
    onSignOut: () -> Unit,
    currentUserProfile: UserProfile?,
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            currentUserProfile?.let { profile ->
                Profile(userProfile = profile)
                Spacer(modifier = Modifier.height(20.dp))
            }


            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSignOut() },
            ) {
                Text(
                    text = stringResource(id = R.string.sign_out),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = TextUnit(4.5f, TextUnitType.Em)
                )
            }
        }
    }
}