package com.app.thingscrossing.feature_account.presentation.registration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BottomNavigationBar
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.EditTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(id = R.string.account)) })
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
        ) {
            Column() {
                Text("RegistrationScreen")
                EditTextField(
                    value = uiState.login,
                    onValueChange = { viewModel.onEvent(RegistrationEvent.LoginChange(it)) },
                    label = R.string.login_label,
                    placeholder = R.string.login_placeholder,
                )



                Button(onClick = { viewModel.onEvent(RegistrationEvent.GetAuthKey) }) {
                    Text(text = "Get auth key")
                }
                Button(onClick = { viewModel.onEvent(RegistrationEvent.SaveAuthKey(uiState.login)) }) {
                    Text(text = "Save auth key")
                }
            }

        }
    }
}