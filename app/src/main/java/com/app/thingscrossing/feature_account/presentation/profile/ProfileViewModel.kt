package com.app.thingscrossing.feature_account.presentation.profile

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class ProfileViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    var uiState by mutableStateOf(ProfileState())
        private set

    init {
        accountUseCases.getCurrentUserProfileByTokenUseCase().onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    uiState = uiState.copy(
                        errorMessageId = resource.messageId,
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    uiState = uiState.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    uiState = uiState.copy(
                        currentProfile = resource.data,
                        isLoading = false,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.DismissError -> {
                uiState = uiState.copy(
                    errorMessageId = null
                )
            }
        }
    }
}