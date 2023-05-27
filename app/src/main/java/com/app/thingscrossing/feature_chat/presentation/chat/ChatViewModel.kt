package com.app.thingscrossing.feature_chat.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_account.presentation.account.AccountViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val accountUseCases: AccountUseCases,
): ViewModel() {
    var companionUserProfileId: Int = savedStateHandle["userId"]!!

    var uiState by mutableStateOf(ChatState())
        private set

    var uiEventFlow = MutableSharedFlow<ChatViewModelEvent>()
        private set

    init {
        getUserProfile()
    }

    fun onEvent(event: ChatEvent) {
        when(event) {
            ChatEvent.DismissError -> {
                uiState = uiState.copy(
                    errorMessageId = null,
                )
            }
        }
    }

    fun sendEvent(event: ChatViewModelEvent) {

    }

    private fun getUserProfile() {
        accountUseCases.getUserProfileById(companionUserProfileId).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    uiState = uiState.copy(
                        errorMessageId = resource.messageId,
                        isLoading = false,
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
                        companionUserProfile = resource.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}