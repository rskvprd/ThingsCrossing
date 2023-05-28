package com.app.thingscrossing.feature_chat.presentation.private_chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_chat.domain.use_case.ChatUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PrivateChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val accountUseCases: AccountUseCases,
    private val chatUseCases: ChatUseCases,
) : ViewModel() {
    val companionUserProfileId: Int = savedStateHandle["userId"]!!
    val roomId: Int = savedStateHandle["roomId"]!!

    var uiState by mutableStateOf(PrivateChatState())
        private set

    var uiEventFlow = MutableSharedFlow<ChatViewModelEvent>()
        private set

    init {
        getUserProfile()
        getMessages()
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            ChatEvent.DismissError -> {
                uiState = uiState.copy(
                    errorMessageId = null,
                )
            }

            is ChatEvent.EditMessageChange -> {
                uiState = uiState.copy(
                    newMessage = event.message,
                )
            }

            ChatEvent.SendMessage -> {
                chatUseCases.sendMessage(text = uiState.newMessage, chatRoomId = roomId)
                    .onEach { resource ->
                        when (resource) {
                            is Resource.Error -> {
                                uiState = uiState.copy(
                                    isMessageUploading = false,
                                    errorMessageId = resource.messageId,
                                )
                            }

                            is Resource.Loading -> {
                                uiState = uiState.copy(
                                    isMessageUploading = true,
                                )
                            }

                            is Resource.Success -> {
                                uiState = uiState.copy(
                                    isMessageUploading = false,
                                    messages = uiState.messages.toMutableList()
                                        .apply { add(resource.data!!) },
                                    newMessage = ""
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }

    fun sendEvent(event: ChatViewModelEvent) {

    }

    private fun getUserProfile() {
        accountUseCases.getUserProfileById(companionUserProfileId).onEach { resource ->
            when (resource) {
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

    private fun getMessages() {
        chatUseCases.getMessagesByRoom(chatRoomId = roomId).onEach { resource ->
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
                        messages = resource.data!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}