package com.app.thingscrossing.feature_chat.domain.repository

import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import com.app.thingscrossing.feature_chat.domain.model.Message
import okhttp3.MultipartBody

interface ChatRepository {
    suspend fun getMessagesByRoom(authKey: String, chatRoomId: Int): List<Message>

    suspend fun getMyRooms(authKey: String): List<ChatRoom>

    suspend fun getOrCreatePrivateRoom(
        authKey: String,
        companionId: Int,
    ): ChatRoom

    suspend fun sendMessage(authKey: String, chatRoomId: Int, text: String): Message
}