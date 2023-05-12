package com.app.thingscrossing.feature_account.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_account.presentation.util.isValidPassword
import com.app.thingscrossing.feature_account.presentation.util.isValidUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases,

    ) : ViewModel() {
    var uiState by mutableStateOf(SignInState())
        private set


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
        }
    }
}