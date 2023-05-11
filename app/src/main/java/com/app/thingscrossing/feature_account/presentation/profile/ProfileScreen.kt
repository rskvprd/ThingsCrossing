package com.app.thingscrossing.feature_account.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.navigation.BottomBarScreens
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.Block

@Composable
fun ProfileScreen(
    navController: NavHostController,
    authKey: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    onSignOut: () -> Unit,
) {
    Block(
        title = stringResource(id = R.string.profile_screen_title),
        description = stringResource(id = R.string.profile_screen_description)) {
        Button(onClick = { onSignOut() }) {
            Text(text = stringResource(id = R.string.sign_out))
        }
    }
}