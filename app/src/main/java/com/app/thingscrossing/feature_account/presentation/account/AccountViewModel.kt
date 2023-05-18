package com.app.thingscrossing.feature_account.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {

    var uiState by mutableStateOf(AccountState())
        private set

    var uiEventFlow = MutableSharedFlow<AccountViewModelEvent>()
        private set

    init {
        getAuthKey()
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            AccountEvent.SignOut -> {
                accountUseCases.deleteAuthKeyUseCase().launchIn(viewModelScope)
                uiState = uiState.copy(
                    authKey = null
                )
            }


            AccountEvent.ChangeHaveAccount -> {
                uiState = uiState.copy(
                    haveAccount = !uiState.haveAccount
                )
            }

            is AccountEvent.SignIn -> {
                accountUseCases.signInUseCase(
                    user = event.user
                ).onEach { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            uiState = uiState.copy(
                                errorMessageId = resource.messageId,
                                isLoading = false,
                            )
                        }

                        is Resource.Loading -> {
                            uiState = uiState.copy(
                                isLoading = true,
                            )
                        }

                        is Resource.Success -> {
                            uiState = uiState.copy(
                                isLoading = false,
                                authKey = resource.data
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }

            AccountEvent.DismissError -> {
                uiState = uiState.copy(
                    errorMessageId = null
                )
            }

            is AccountEvent.SignUp -> {
                accountUseCases.signUpUseCase(
                    event.user
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
                            accountUseCases.saveAuthKeyUseCase(resource.data!!.token).collect()
                            uiState = uiState.copy(
                                isLoading = false,
                                authKey = resource.data.token,
                                currentUser = resource.data.user
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun getAuthKey () {
        viewModelScope.launch {
            accountUseCases.getAuthKeyUseCase().collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        uiState = uiState.copy(
                            isLoading = false,
                            errorMessageId = resource.messageId
                        )
                    }

                    is Resource.Loading -> {
                        uiState = uiState.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        uiState = uiState.copy(
                            isLoading = false,
                            authKey = resource.data
                        )
                    }
                }
            }
        }
    }
}