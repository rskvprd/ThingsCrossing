package com.app.thingscrossing.feature_chat.presentation.rooms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom

@Composable
fun ChatRoomList(
    chatRooms: List<ChatRoom>,
    myProfile: UserProfile,
    onPrivateRoom: (companion: UserProfile, ChatRoom) -> Unit,
) {
    LazyColumn(content = {
        items(chatRooms) { chatRoom ->
            PrivateRoomItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                chatRoom = chatRoom,
                me = myProfile,
                onRoomClick = onPrivateRoom
            )
        }
    })
}