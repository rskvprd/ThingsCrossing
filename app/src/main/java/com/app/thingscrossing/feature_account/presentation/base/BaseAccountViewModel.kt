package com.app.thingscrossing.feature_account.presentation.base

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
class BaseAccountViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {

    var uiState by mutableStateOf(BaseAccountState())
        private set

    var eventFlow = MutableSharedFlow<BaseAccountViewModelEvent>()
        private set

    init {
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

    fun onEvent(event: BaseAccountEvent) {
        when (event) {
            BaseAccountEvent.SignOut -> {
                accountUseCases.deleteAuthKeyUseCase().launchIn(viewModelScope)
                uiState = uiState.copy(
                    authKey = null
                )
            }


            BaseAccountEvent.ChangeHaveAccount -> {
                uiState = uiState.copy(
                    haveAccount = !uiState.haveAccount
                )
            }

            is BaseAccountEvent.SignIn -> {
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

            BaseAccountEvent.DismissError -> {
                uiState = uiState.copy(
                    errorMessageId = null
                )
            }

            is BaseAccountEvent.SignUp -> {
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
                                authKey = resource.data.token
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}