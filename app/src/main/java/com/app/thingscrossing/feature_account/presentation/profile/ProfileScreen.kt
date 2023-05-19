package com.app.thingscrossing.feature_account.presentation.profile

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_account.presentation.profile.components.Profile
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.Block
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.LoadingDialog

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

        currentUserProfile?.let {  profile ->
            Log.d("asdfsad", currentUserProfile.toString())
            Profile(userProfile = profile)
            Spacer(modifier = Modifier.height(20.dp))
        }


        Button(onClick = { onSignOut() }) {
            Text(text = stringResource(id = R.string.sign_out))
        }
    }
}