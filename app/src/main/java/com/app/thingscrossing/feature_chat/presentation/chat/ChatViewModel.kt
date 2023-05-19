package com.app.thingscrossing.feature_chat.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(

): ViewModel() {
    var uiState by mutableStateOf(ChatState())
        private set

    var uiEventFlow = MutableSharedFlow<ChatViewModelEvent>()
        private set

    fun onEvent(event: ChatEvent) {

    }

    fun sendEvent(event: ChatViewModelEvent) {

    }
}