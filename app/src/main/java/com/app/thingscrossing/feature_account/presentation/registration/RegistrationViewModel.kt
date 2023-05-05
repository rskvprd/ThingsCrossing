package com.app.thingscrossing.feature_account.presentation.registration

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.data.local.authDataStore
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {
    var uiState: RegistrationState by mutableStateOf(RegistrationState())
        private set


    init {
        getAuthKey()
    }

    fun sendEvent() {

    }

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.GetAuthKey -> {
                getAuthKey()
            }

            is RegistrationEvent.SaveAuthKey -> {
                accountUseCases.saveAuthKeyUseCase(event.key).launchIn(viewModelScope)
            }

            is RegistrationEvent.LoginChange -> {
                uiState = uiState.copy(
                    login = event.login
                )
            }

            is RegistrationEvent.EmailChange -> {
                uiState = uiState.copy(
                    email = event.email
                )
            }
            is RegistrationEvent.PasswordChange -> {
                uiState = uiState.copy(
                    password = event.password
                )
            }
            is RegistrationEvent.SecondPasswordChange -> {
                uiState = uiState.copy(
                    secondPassword = event.secondPassword
                )
            }
        }
    }

    private fun getAuthKey() {
        accountUseCases.getAuthKeyUseCase().onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    Log.d("ASD", "error")
                }

                is Resource.Loading -> {
                    Log.d("ASD", "isLoading")
                }

                is Resource.Success -> {
                    Log.d("ASD", resource.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}