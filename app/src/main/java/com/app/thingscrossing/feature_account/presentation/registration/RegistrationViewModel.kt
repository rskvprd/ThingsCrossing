package com.app.thingscrossing.feature_account.presentation.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_account.presentation.util.AccountScreen
import com.app.thingscrossing.feature_account.presentation.util.isValidEmail
import com.app.thingscrossing.feature_account.presentation.util.isValidFirstName
import com.app.thingscrossing.feature_account.presentation.util.isValidLastName
import com.app.thingscrossing.feature_account.presentation.util.isValidPassword
import com.app.thingscrossing.feature_account.presentation.util.isValidUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {
    var uiState: RegistrationState by mutableStateOf(RegistrationState())
        private set

    var eventFlow = MutableSharedFlow<RegistrationViewModelEvent>()
        private set


    private fun sendEvent(event: RegistrationViewModelEvent) {
        viewModelScope.launch {
            eventFlow.emit(event)
        }
    }

    fun onEvent(event: RegistrationEvent) {
        when (event) {

            is RegistrationEvent.SaveAuthKey -> {
                accountUseCases.saveAuthKeyUseCase(event.key).launchIn(viewModelScope)
            }

            is RegistrationEvent.UsernameChange -> {
                uiState = uiState.copy(
                    username = event.username,
                    isUsernameValid = event.username.isValidUsername(),
                )
            }

            is RegistrationEvent.EmailChange -> {
                uiState = uiState.copy(
                    email = event.email,
                    isEmailValid = event.email.isValidEmail(),
                )
            }

            is RegistrationEvent.PasswordChange -> {
                uiState = uiState.copy(
                    password = event.password,
                    isPasswordValid = event.password.isValidPassword(),
                )
            }

            is RegistrationEvent.SecondPasswordChange -> {
                uiState = uiState.copy(
                    secondPassword = event.secondPassword,
                    isSecondPasswordValid = event.secondPassword == uiState.password
                )
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

            is RegistrationEvent.FirstNameChange -> {
                uiState = uiState.copy(
                    firstName = event.firstName,
                    isFirstNameValid = event.firstName.isValidFirstName(),
                )
            }

            is RegistrationEvent.LastNameChange -> {
                uiState = uiState.copy(
                    lastName = event.lastName,
                    isLastNameValid = event.lastName.isValidLastName(),
                )
            }
        }
    }
}