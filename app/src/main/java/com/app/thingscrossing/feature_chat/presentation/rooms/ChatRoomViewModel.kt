package com.app.thingscrossing.feature_chat.presentation.rooms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_chat.domain.use_case.ChatUseCases
import com.app.thingscrossing.feature_chat.navigation.ChatScreens
import com.app.thingscrossing.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val authService: AuthService,
) : ViewModel() {
    var uiState by mutableStateOf(ChatRoomState())
        private set

    var eventFlow = MutableSharedFlow<ChatRoomViewModelEvent>()
        private set

    val currentUserProfile = authService.currentUserProfile

    init {
        getRooms()
    }

    private fun sendEvent(event: ChatRoomViewModelEvent) {
        viewModelScope.launch {
            eventFlow.emit(event)
        }
    }

    fun onEvent(event: ChatRoomEvent) {
        when (event) {
            ChatRoomEvent.DismissError -> {
                uiState = uiState.copy(
                    errorMessageId = null
                )
            }

            is ChatRoomEvent.ToPrivateChat -> {
                sendEvent(
                    ChatRoomViewModelEvent.Navigate(
                        route = ChatScreens.ChatScreen.route +
                                "?userId=${event.profile.id}&roomId=${event.room.id}"
                    )
                )
            }
        }
    }

    private fun getRooms() {
        viewModelScope.launch {
            while (!authService.isInitialized) delay(10)
            if (authService.isAuthenticated) {
                uiState = uiState.copy(
                    isAuthorized = true,
                )
                chatUseCases.getMyRooms(authService.authKey!!).onEach { resource ->
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
                                isLoading = false,
                                chatRooms = resource.data!!
                            )
                        }
                    }
                }.collect()
            } else {
                uiState = uiState.copy(
                    isAuthorized = false,
                )
            }
        }
    }
}