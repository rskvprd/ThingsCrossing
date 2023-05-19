package com.app.thingscrossing.feature_account.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Constants
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        if (uiState.authKey == null) {
            loadAuthKeyAndUserProfile()
        }
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
                            accountUseCases.saveAuthKeyUseCase(resource.data!!)
                                .launchIn(viewModelScope)
                            loadToStateUserProfileByAuthKey(resource.data)
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
                                currentUserProfile = resource.data.profile.copy(avatar = "${Constants.THINGS_CROSSING_API_BASE_URL}${resource.data.profile.avatar}")
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun loadToStateUserProfileByAuthKey(authKey: String) {
        accountUseCases.getCurrentUserProfileByTokenUseCase(authKey).onEach { profileResource ->
            when (profileResource) {
                is Resource.Error -> {
                    uiState = uiState.copy(
                        errorMessageId = profileResource.messageId,
                        isLoading = false,
                    )
                }

                is Resource.Loading -> {
                    uiState = uiState.copy(
                        isLoading = true,
                        errorMessageId = null
                    )
                }

                is Resource.Success -> {
                    val profile = profileResource.data!!
                    uiState = uiState.copy(
                        authKey = authKey,
                        currentUserProfile = profile,
                        errorMessageId = null,
                        isLoading = false,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun loadAuthKeyAndUserProfile() {
        accountUseCases.getAuthKeyUseCase().onEach { resource ->
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
                    if (resource.data != null) {
                        loadToStateUserProfileByAuthKey(resource.data)
                    } else {
                        uiState = uiState.copy(isLoading = false)
                    }
                }
            }
        }.launchIn(viewModelScope)

    }
}