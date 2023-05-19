package com.app.thingscrossing.feature_chat.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_chat.domain.model.Message
import com.app.thingscrossing.feature_chat.presentation.chat.components.CompanionProfileTopBar
import com.app.thingscrossing.feature_chat.presentation.chat.components.MessageList
import com.app.thingscrossing.ui.theme.ThingsCrossingTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ChatScreen(
    currentUserProfile: UserProfile,
    companionUserProfile: UserProfile,
    newMessageValue: String,
    onEditNewMessage: (String) -> Unit,
    uiState: ChatState,
    uiEventFlow: SharedFlow<ChatViewModelEvent>,
    onEvent: (ChatEvent) -> Unit,
    messages: List<Message>,
) {
    Scaffold(
        topBar = {
            CompanionProfileTopBar(profile = companionUserProfile)
        },
        bottomBar = {
            TextField(value = newMessageValue, onValueChange = { onEditNewMessage(it) })
        }
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {
            uiState.messages.map {
                MessageList(
                    messages = messages,
                    me = currentUserProfile
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ThingsCrossingTheme() {
        ChatScreen(
            currentUserProfile = UserProfile.DEFAULT_ME,
            companionUserProfile = UserProfile.DEFAULT_COMPANION,
            newMessageValue = "Новое сообщение",
            onEditNewMessage = {},
            uiState = ChatState(),
            uiEventFlow = MutableSharedFlow(),
            onEvent = {},
            messages = listOf(
                Message(
                    id = 1,
                    room = 1,
                    sentDateTime = "2023.12.10T15:52:12",
                    text = "Hello",
                    toUser = UserProfile.DEFAULT_ME,
                    fromUser = UserProfile.DEFAULT_COMPANION
                ),
                Message(
                    id = 2,
                    room = 1,
                    sentDateTime = "2023.12.11T15:52:12",
                    text = "World",
                    toUser = UserProfile.DEFAULT_ME,
                    fromUser = UserProfile.DEFAULT_COMPANION
                ),
                Message(
                    id = 3,
                    room = 1,
                    sentDateTime = "2023.12.12T15:52:12",
                    text = "Hello from companion",
                    toUser = UserProfile.DEFAULT_COMPANION,
                    fromUser = UserProfile.DEFAULT_ME
                ),
            )
        )
    }
}