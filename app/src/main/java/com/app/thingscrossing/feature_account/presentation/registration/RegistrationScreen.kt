package com.app.thingscrossing.feature_account.presentation.registration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BottomNavigationBar
import com.app.thingscrossing.feature_account.presentation.util.AccountScreen
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.Block
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
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
        ) {

            Block(
                title = stringResource(id = R.string.registration),
                description = stringResource(
                    id = R.string.registration_description
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    EditTextField(
                        value = uiState.login,
                        keyboardType = KeyboardType.Ascii,
                        onValueChange = { viewModel.onEvent(RegistrationEvent.LoginChange(it)) },
                        label = R.string.login_label,
                        placeholder = R.string.login_placeholder,
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    EditTextField(
                        value = uiState.email,
                        onValueChange = { viewModel.onEvent(RegistrationEvent.EmailChange(it)) },
                        label = R.string.email_label,
                        keyboardType = KeyboardType.Email,
                        placeholder = R.string.email_placeholder,
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    EditTextField(
                        value = uiState.password,
                        keyboardType = KeyboardType.Password,
                        onValueChange = { viewModel.onEvent(RegistrationEvent.PasswordChange(it)) },
                        label = R.string.password_label,
                        placeholder = R.string.password_placeholder,
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    EditTextField(
                        value = uiState.secondPassword,
                        keyboardType = KeyboardType.Password,
                        onValueChange = {
                            viewModel.onEvent(
                                RegistrationEvent.SecondPasswordChange(
                                    it
                                )
                            )
                        },
                        label = R.string.second_password_label,
                        placeholder = R.string.password_placeholder,
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Button(
                        onClick = { viewModel.onEvent(RegistrationEvent.GetAuthKey) }
                    ) {
                        Text(
                            text = stringResource(id = R.string.make_register),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    TextButton(onClick = { navController.navigate(AccountScreen.LoginScreen.route) }) {
                        Text(text = stringResource(id = R.string.already_have_account))
                    }
                }


            }

        }
    }
}