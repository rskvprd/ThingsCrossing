package com.app.thingscrossing.feature_account.presentation.sign_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.navigation.AccountScreens
import com.app.thingscrossing.feature_account.presentation.sign_up.components.PasswordField
import com.app.thingscrossing.feature_account.presentation.sign_up.components.TextFieldWithValidation
import com.app.thingscrossing.feature_account.presentation.sign_up.components.UsernameField
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.Block
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.LoadingDialog
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel,
) {
    val uiState = viewModel.uiState

    LaunchedEffect(key1 = null) {
        if (viewModel.isAuthenticated) {
            navController.popBackStack()
            navController.navigate(AccountScreens.ProfileScreen.route)
        }

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SignUpViewModelEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    if (uiState.isLoading) {
        LoadingDialog(progression = null)
    }

    if (uiState.errorMessageId != null) {
        ErrorDialog(onDismissError = { viewModel.onEvent(SignUpEvent.DismissError) }, errorMessageId = uiState.errorMessageId)
    }

    Block(
        title = stringResource(id = R.string.registration), description = stringResource(
            id = R.string.registration_description
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FirstNameField(uiState = uiState, viewModel = viewModel)
            LastNameField(uiState = uiState, viewModel = viewModel)
            UsernameField(
                usernameValue = uiState.username,
                onUsernameChange = { username ->
                    viewModel.onEvent(
                        SignUpEvent.UsernameChange(
                            username
                        )
                    )
                },
                isUsernameValid = uiState.isUsernameValid
            )

            Spacer(modifier = Modifier.padding(10.dp))

            EmailField(uiState = uiState, viewModel = viewModel)
            PasswordField(
                passwordValue = uiState.password,
                onPasswordChange = { password ->
                    viewModel.onEvent(SignUpEvent.PasswordChange(password))
                },
                isPasswordValid = uiState.isPasswordValid,
                onVisibilityChange = {
                    viewModel.onEvent(SignUpEvent.ToggleShowPassword)
                },
                isPasswordVisible = uiState.isPasswordVisible
            )
            PasswordAgainField(uiState = uiState, viewModel = viewModel)

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isValid(),
                onClick = {
                    viewModel.onEvent(SignUpEvent.SignUp)
                }) {
                Text(
                    text = stringResource(id = R.string.make_register),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            TextButton(onClick = { viewModel.onEvent(SignUpEvent.ToSignInScreen) }) {
                Text(text = stringResource(id = R.string.already_have_account))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FirstNameField(
    uiState: SignUpState,
    viewModel: SignUpViewModel
) {
    TextFieldWithValidation(
        value = uiState.firstName,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),
        onValueChange = { firstName ->
            viewModel.onEvent(
                SignUpEvent.FirstNameChange(
                    firstName
                )
            )
        },
        label = R.string.first_name_label,
        placeholder = R.string.first_name_placeholder,
        isValid = uiState.isFirstNameValid,
        invalidText = stringResource(id = R.string.first_name_invalid),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LastNameField(
    uiState: SignUpState,
    viewModel: SignUpViewModel
) {
    TextFieldWithValidation(
        value = uiState.lastName,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),
        onValueChange = { lastName ->
            viewModel.onEvent(
                SignUpEvent.LastNameChange(
                    lastName
                )
            )
        },
        label = R.string.last_name_label,
        placeholder = R.string.last_name_placeholder,
        isValid = uiState.isLastNameValid,
        invalidText = stringResource(id = R.string.last_name_invalid),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    uiState: SignUpState,
    viewModel: SignUpViewModel
) {
    TextFieldWithValidation(
        value = uiState.email,
        onValueChange = { email ->
            viewModel.onEvent(
                SignUpEvent.EmailChange(
                    email
                )
            )
        },
        label = R.string.email_label,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        placeholder = R.string.email_placeholder,
        isValid = uiState.isEmailValid,
        invalidText = stringResource(id = R.string.email_invalid)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordAgainField(
    uiState: SignUpState,
    viewModel: SignUpViewModel
) {
    TextFieldWithValidation(
        value = uiState.secondPassword,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        onValueChange = { secondPassword ->
            viewModel.onEvent(
                SignUpEvent.SecondPasswordChange(secondPassword)
            )
        },
        label = R.string.second_password_label,
        placeholder = R.string.password_placeholder,
        isValid = uiState.isSecondPasswordValid,
        invalidText = stringResource(id = R.string.second_password_invalid),
        trailingIcon = {
            IconButton(onClick = { viewModel.onEvent(SignUpEvent.ToggleShowSecondPassword) }) {
                Icon(
                    if (uiState.isSecondPasswordVisible) {
                        Icons.Default.VisibilityOff
                    } else {
                        Icons.Default.Visibility
                    },
                    stringResource(id = R.string.toggle_show_second_password_icon_description)
                )
            }
        },
        visualTransformation = if (uiState.isSecondPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}