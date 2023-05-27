package com.app.thingscrossing.feature_chat.domain.repository

import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import com.app.thingscrossing.feature_chat.domain.model.Message

interface ChatRepository {
    suspend fun getMessagesByRoom(authKey: String, chatRoom: ChatRoom): List<Message>

    suspend fun getMyRooms(authKey: String): List<ChatRoom>

    suspend fun getOrCreatePrivateRoom(
        authKey: String,
        companionId: Int,
    ): ChatRoom

    suspend fun sendMessage(authKey: String, chatRoom: ChatRoom, text: String)
}