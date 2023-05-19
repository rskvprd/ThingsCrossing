package com.app.thingscrossing.feature_chat.presentation.chat

import com.app.thingscrossing.feature_chat.domain.model.Message

data class ChatState(
    val isLoading: Boolean = false,
    val errorMessageId: Int? = null,
    val messages: List<Message> = emptyList()
)