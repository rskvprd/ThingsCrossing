package com.app.thingscrossing.feature_account.presentation.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.navigation.AccountScreens
import com.app.thingscrossing.feature_account.presentation.sign_up.components.PasswordField
import com.app.thingscrossing.feature_account.presentation.sign_up.components.UsernameField
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.Block
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ErrorDialog
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel,
) {
    val uiState = viewModel.uiState
    val uiEventFlow = viewModel.uiEventFlow



    LaunchedEffect(key1 = null) {
        if (viewModel.isAuthenticated) {
            navController.navigate(AccountScreens.ProfileScreen.route)
        }

        uiEventFlow.collectLatest { event ->
            when (event) {
                is SignInViewModelEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    if (uiState.errorMessageId != null) {
        ErrorDialog(
            onDismissError = { viewModel.onEvent(SignInEvent.DismissError) },
            errorMessageId = uiState.errorMessageId
        )
    }

    Block(
        title = stringResource(id = R.string.sign_in_title),
        description = stringResource(id = R.string.sign_in_description)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                isPasswordVisible = uiState.isPasswordVisible,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions{
                    viewModel.onEvent(SignInEvent.SignIn)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isValid(),
                onClick = {
                    viewModel.onEvent(SignInEvent.SignIn)
                }) {
                Text(
                    text = stringResource(id = R.string.sign_in_button_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            TextButton(onClick = { viewModel.onEvent(SignInEvent.ToSignUpScreen) }) {
                Text(text = stringResource(id = R.string.does_not_have_account))
            }
        }
    }

}