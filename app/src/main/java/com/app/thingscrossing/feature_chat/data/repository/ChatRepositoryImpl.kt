package com.app.thingscrossing.feature_chat.data.repository

import com.app.thingscrossing.feature_chat.data.remote.ChatApi
import com.app.thingscrossing.feature_chat.data.remote.request.GetOrCreatePrivateRoomRequest
import com.app.thingscrossing.feature_chat.data.remote.request.SendMessageRequest
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import com.app.thingscrossing.feature_chat.domain.model.Message
import com.app.thingscrossing.feature_chat.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi
) : ChatRepository {
    override suspend fun getMessagesByRoom(authKey: String, chatRoom: ChatRoom): List<Message> =
        withContext(Dispatchers.IO) {
            chatApi.getMessages(authKey = authKey, chatRoom = chatRoom)
        }

    override suspend fun getMyRooms(authKey: String): List<ChatRoom> =
        withContext(Dispatchers.IO) {
            chatApi.getRooms(authKey = authKey)
        }

    override suspend fun getOrCreatePrivateRoom(
        authKey: String,
        companionId: Int
    ): ChatRoom =
        withContext(Dispatchers.IO) {
            chatApi.getOrCreatePrivateRoom(
                authKey = authKey,
                request = GetOrCreatePrivateRoomRequest(
                    companionId = companionId
                )
            )
        }

    override suspend fun sendMessage(authKey: String, chatRoom: ChatRoom, text: String) =
        withContext(Dispatchers.IO) {
            chatApi.sendMessage(
                authKey = authKey,
                request = SendMessageRequest(
                    text = text,
                    room = chatRoom.id,
                )
            )
        }
}