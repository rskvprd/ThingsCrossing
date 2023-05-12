package com.app.thingscrossing.feature_account.presentation.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.presentation.registration.components.PasswordField
import com.app.thingscrossing.feature_account.presentation.registration.components.UsernameField
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.Block

@Composable
fun SignInScreen(
    navController: NavController,
    onChangeHaveAccount: () -> Unit,
    onSignIn: (User) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    Block(
        title = stringResource(id = R.string.sign_in_title),
        description = stringResource(id = R.string.sign_in_description)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            UsernameField(
                usernameValue = uiState.username,
                onUsernameChange = { username ->
                    viewModel.onEvent(
                        SignInEvent.UsernameChange(
                            username
                        )
                    )
                },
                isUsernameValid = true
            )

            PasswordField(
                passwordValue = uiState.password,
                onPasswordChange = { password ->
                    viewModel.onEvent(
                        SignInEvent.PasswordChange(
                            password
                        )
                    )
                },
                isPasswordValid = true,
                onVisibilityChange = { viewModel.onEvent(SignInEvent.ChangePasswordVisibility) },
                isPasswordVisible = uiState.isPasswordVisible
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                enabled = uiState.isValid(),
                onClick = {
                    onSignIn(
                        User(
                            username = uiState.username,
                            password = uiState.password
                        )
                    )
                }) {
                Text(
                    text = stringResource(id = R.string.sign_in_button_text)
                )
            }

            TextButton(onClick = { onChangeHaveAccount() }) {
                Text(text = stringResource(id = R.string.does_not_have_account))
            }
        }
    }

}