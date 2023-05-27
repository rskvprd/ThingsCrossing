package com.app.thingscrossing.feature_chat.presentation.rooms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_chat.domain.use_case.ChatUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
) : ViewModel() {
    var uiState by mutableStateOf(ChatRoomState())
        private set

    init {
        getRooms()
    }

    fun onEvent(event: ChatRoomEvent) {
        when(event){
            ChatRoomEvent.DismissError -> {
                uiState = uiState.copy(
                    errorMessageId = null
                )
            }
        }
    }

    private fun getRooms() {
        chatUseCases.getMyRooms().onEach { resource ->
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
        }.launchIn(viewModelScope)
    }

    private fun checkIsSignedIn() {

    }
}