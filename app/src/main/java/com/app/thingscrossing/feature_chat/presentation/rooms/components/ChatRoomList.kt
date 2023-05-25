package com.app.thingscrossing.feature_chat.presentation.rooms.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom

@Composable
fun ChatRoomList(chatRooms: List<ChatRoom>) {
    LazyColumn(content = {
        items(chatRooms) { chatRoom ->
            ChatRoomItem(chatRoom = chatRoom)
        }
    })
}