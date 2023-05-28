package com.app.thingscrossing.feature_chat.presentation.private_chat.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_chat.domain.model.Message

@Composable
fun MessageList(
    messages: List<Message>,
    me: UserProfile,
) {
    if (messages.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(id = R.string.no_messages))
        }
    }
    LazyColumn(Modifier.fillMaxSize()) {
        items(messages.size) { index ->
            val message = messages[index]

            MessageItem(
                message = message,
                isMe = message.fromUser.id == me.id
            )
        }
    }
}