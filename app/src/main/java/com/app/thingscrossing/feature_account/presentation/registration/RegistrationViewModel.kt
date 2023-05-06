package com.app.thingscrossing.feature_account.presentation.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.feature_account.domain.model.Credentials
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {
    var uiState: RegistrationState by mutableStateOf(RegistrationState())
        private set


    fun sendEvent() {

    }

    fun onEvent(event: RegistrationEvent) {
        when (event) {

            is RegistrationEvent.SaveAuthKey -> {
                accountUseCases.saveAuthKeyUseCase(event.key).launchIn(viewModelScope)
            }

            is RegistrationEvent.LoginChange -> {
                uiState = uiState.copy(
                    username = event.username,
                    isUsernameValid = event.username.isValidUsername()
                )
                validateFormFromState()
            }

            is RegistrationEvent.EmailChange -> {
                uiState = uiState.copy(
                    email = event.email,
                    isEmailValid = event.email.isValidEmail()
                )
                validateFormFromState()
            }

            is RegistrationEvent.PasswordChange -> {
                uiState = uiState.copy(
                    password = event.password,
                    isPasswordValid = event.password.isValidPassword()
                )
                validateFormFromState()
            }

            is RegistrationEvent.SecondPasswordChange -> {
                uiState = uiState.copy(
                    secondPassword = event.secondPassword,
                    isSecondPasswordValid = event.secondPassword == uiState.password
                )
                validateFormFromState()
            }

            RegistrationEvent.SignUp -> {
                accountUseCases.signUpUseCase(
                    Credentials(
                        email = uiState.email,
                        username = uiState.username,
                        password = uiState.password
                    )
                ).launchIn(viewModelScope)
            }

            RegistrationEvent.ToggleShowPassword -> {
                uiState = uiState.copy(
                    isPasswordVisible = !uiState.isPasswordVisible
                )
            }

            RegistrationEvent.ToggleShowSecondPassword -> {
                uiState = uiState.copy(
                    isSecondPasswordVisible = !uiState.isSecondPasswordVisible
                )
            }
        }
    }

    private fun String.isValidPassword(): Boolean {
        return this.length > 4
    }

    private fun String.isValidUsername(): Boolean {
        return this.length > 4
    }

    private fun String.isValidEmail(): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return this.matches(emailRegex.toRegex())
    }

    private fun validateFormFromState() {
        val isValidState = uiState.email.isValidEmail() &&
                uiState.username.isValidUsername() &&
                uiState.password.isValidPassword() &&
                uiState.password == uiState.secondPassword
        uiState = uiState.copy(registrationAvailable = isValidState)
    }


}