package com.app.thingscrossing.feature_chat.domain.repository

import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import com.app.thingscrossing.feature_chat.domain.model.Message

interface ChatRepository {
    fun getMessagesByRoom(chatRoom: ChatRoom, authKey: String): List<Message>
    fun getMyRooms(authKey: String)
}