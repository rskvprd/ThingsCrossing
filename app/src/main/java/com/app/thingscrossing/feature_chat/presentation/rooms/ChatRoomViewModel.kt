package com.app.thingscrossing.feature_chat.presentation.rooms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
) : ViewModel() {
    var uiState by mutableStateOf(ChatRoomState())
        private set

    init {

    }

    fun onEvent(event: ChatRoomEvent) {
        when(event){

            else -> {}
        }
    }
}