package com.app.thingscrossing.feature_chat.presentation.rooms.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom

@Composable
fun ChatRoomList(chatRooms: List<ChatRoom>) {
    if (chatRooms.isEmpty()) {
        Text(text = stringResource(id = R.string.no_chat_rooms))
    }
    LazyColumn(content = {
        items(chatRooms) { chatRoom ->
            ChatRoomItem(chatRoom = chatRoom)
        }
    })
}