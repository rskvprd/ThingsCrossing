package com.app.thingscrossing.feature_chat.presentation.chat.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_chat.domain.model.Message

@Composable
fun MessageList(
    messages: List<Message>,
    me: UserProfile,
) {
    LazyColumn{
        items(messages.size) {index ->
            MessageItem(
                message = messages[index],
                isMe = messages[index].fromUser == me
            )
        }
    }
}

@Composable
fun MessageItem(
    message: Message,
    isMe: Boolean,
) {
    Card(modifier = Modifier
        .width(100.dp)
        .height(50.dp)) {

    }
}