package com.app.thingscrossing.feature_account.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_account.navigation.AccountScreens
import com.app.thingscrossing.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases,
    private val authService: AuthService
) : ViewModel() {
    var uiState by mutableStateOf(SignInState())
        private set

    val uiEventFlow = MutableSharedFlow<SignInViewModelEvent>()

    val isAuthenticated = authService.isAuthenticated

    init {
        viewModelScope.launch {
            while (!authService.isInitialized) {
                delay(10)
            }
            if (authService.isAuthenticated) {
                sendEvent(SignInViewModelEvent.Navigate(AccountScreens.ProfileScreen.route))
            }
        }
    }

    private fun sendEvent(event: SignInViewModelEvent) {
        viewModelScope.launch {
            uiEventFlow.emit(event)
        }
    }

    fun onEvent(event: SignInEvent) {
        when (event) {
            SignInEvent.ChangePasswordVisibility -> {
                uiState = uiState.copy(
                    isPasswordVisible = !uiState.isPasswordVisible
                )
            }

            is SignInEvent.PasswordChange -> {
                uiState = uiState.copy(
                    password = event.password
                )
            }

            is SignInEvent.UsernameChange -> {
                uiState = uiState.copy(
                    username = event.username
                )
            }

            SignInEvent.SignIn -> {
                authService.signInWithUsernameAndPassword(
                    uiState.username,
                    uiState.password,
                    onSuccess = {
                        uiState = uiState.copy(
                            isLoading = false,
                        )
                        sendEvent(SignInViewModelEvent.Navigate(AccountScreens.ProfileScreen.route))
                    },
                    onError = {
                        uiState = uiState.copy(
                            isLoading = false,
                            errorMessageId = it
                        )
                    },
                    onLoading = {
                        uiState = uiState.copy(
                            isLoading = true
                        )
                    }
                )
            }

            SignInEvent.ToSignUpScreen -> {
                sendEvent(SignInViewModelEvent.Navigate(AccountScreens.SignUpScreen.route))
            }

            SignInEvent.DismissError -> uiState = uiState.copy(errorMessageId = null)
        }
    }
}