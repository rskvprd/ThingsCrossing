package com.app.thingscrossing.feature_account.presentation.registration

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.core.navigation.BottomBarScreens
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_account.presentation.util.AccountScreen
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
                validateFormFromState()
            }

            is RegistrationEvent.EmailChange -> {
                uiState = uiState.copy(
                    email = event.email,
                    isEmailValid = event.email.isValidEmail(),
                )
                validateFormFromState()
            }

            is RegistrationEvent.PasswordChange -> {
                uiState = uiState.copy(
                    password = event.password,
                    isPasswordValid = event.password.isValidPassword(),
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
                    User(
                        email = uiState.email,
                        username = uiState.username,
                        password = uiState.password,
                        firstName = uiState.firstName,
                        lastName = uiState.lastName
                    )
                ).onEach { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            uiState = uiState.copy(
                                isLoading = false, errorMessageId = resource.messageId
                            )
                        }

                        is Resource.Loading -> {
                            uiState = uiState.copy(
                                isLoading = true,
                            )
                        }

                        is Resource.Success -> {
                            uiState = uiState.copy(isLoading = false)
                            accountUseCases.saveAuthKeyUseCase(resource.data!!.token)
                            sendEvent(
                                RegistrationViewModelEvent.NavigateEvent(
                                    AccountScreen.LoginScreen.route
                                )
                            )
                        }
                    }
                }.launchIn(viewModelScope)
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
                validateFormFromState()
            }

            is RegistrationEvent.LastNameChange -> {
                uiState = uiState.copy(
                    lastName = event.lastName,
                    isLastNameValid = event.lastName.isValidLastName(),
                )
                validateFormFromState()
            }
        }
    }


    private fun validateFormFromState() {
        uiState = uiState.copy(registrationAvailable = uiState.isValid())
    }

}