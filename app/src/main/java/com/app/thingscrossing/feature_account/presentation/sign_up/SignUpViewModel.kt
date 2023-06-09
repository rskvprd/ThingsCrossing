package com.app.thingscrossing.feature_account.presentation.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_account.navigation.AccountScreens
import com.app.thingscrossing.feature_account.presentation.util.isValidEmail
import com.app.thingscrossing.feature_account.presentation.util.isValidFirstName
import com.app.thingscrossing.feature_account.presentation.util.isValidLastName
import com.app.thingscrossing.feature_account.presentation.util.isValidPassword
import com.app.thingscrossing.feature_account.presentation.util.isValidUsername
import com.app.thingscrossing.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases,
    private val authService: AuthService,
) : ViewModel() {
    var uiState: SignUpState by mutableStateOf(SignUpState())
        private set

    var eventFlow = MutableSharedFlow<SignUpViewModelEvent>()
        private set

    val isAuthenticated = authService.isAuthenticated

    init {
        viewModelScope.launch {
            while (!authService.isInitialized) {
                delay(10)
            }
            if (authService.isAuthenticated) {
                sendEvent(SignUpViewModelEvent.Navigate(AccountScreens.ProfileScreen.route))
            }
        }
    }

    private fun sendEvent(event: SignUpViewModelEvent) {
        viewModelScope.launch {
            eventFlow.emit(event)
        }
    }

    fun onEvent(event: SignUpEvent) {
        when (event) {

            is SignUpEvent.SaveAuthKey -> {
                accountUseCases.saveAuthKeyUseCase(event.key).launchIn(viewModelScope)
            }

            is SignUpEvent.UsernameChange -> {
                uiState = uiState.copy(
                    username = event.username,
                    isUsernameValid = event.username.isValidUsername(),
                )
            }

            is SignUpEvent.EmailChange -> {
                uiState = uiState.copy(
                    email = event.email,
                    isEmailValid = event.email.isValidEmail(),
                )
            }

            is SignUpEvent.PasswordChange -> {
                uiState = uiState.copy(
                    password = event.password,
                    isPasswordValid = event.password.isValidPassword(),
                )
            }

            is SignUpEvent.SecondPasswordChange -> {
                uiState = uiState.copy(
                    secondPassword = event.secondPassword,
                    isSecondPasswordValid = event.secondPassword == uiState.password
                )
            }

            SignUpEvent.ToggleShowPassword -> {
                uiState = uiState.copy(
                    isPasswordVisible = !uiState.isPasswordVisible
                )
            }

            SignUpEvent.ToggleShowSecondPassword -> {
                uiState = uiState.copy(
                    isSecondPasswordVisible = !uiState.isSecondPasswordVisible
                )
            }

            is SignUpEvent.FirstNameChange -> {
                uiState = uiState.copy(
                    firstName = event.firstName,
                    isFirstNameValid = event.firstName.isValidFirstName(),
                )
            }

            is SignUpEvent.LastNameChange -> {
                uiState = uiState.copy(
                    lastName = event.lastName,
                    isLastNameValid = event.lastName.isValidLastName(),
                )
            }

            SignUpEvent.SignUp -> {
                uiState = uiState.validate()
                if (uiState.isValid()) {
                    authService.signUpWithCredentials(
                        username = uiState.username,
                        firstName = uiState.firstName,
                        lastName = uiState.lastName,
                        password = uiState.password,
                        email = uiState.email,
                        onSuccess = {
                            uiState = uiState.copy(
                                isLoading = false
                            )
                            sendEvent(SignUpViewModelEvent.Navigate(AccountScreens.ProfileScreen.route))
                        },
                        onLoading = {
                            uiState = uiState.copy(
                                isLoading = true
                            )
                        },
                        onError = {
                            uiState = uiState.copy(
                                errorMessageId = it,
                                isLoading = false
                            )
                        }
                    )
                }
            }

            SignUpEvent.ToSignInScreen -> {
                sendEvent(SignUpViewModelEvent.Navigate(AccountScreens.SignInScreen.route))
            }

            SignUpEvent.DismissError -> uiState = uiState.copy(errorMessageId = null)
        }
    }
}