package com.app.thingscrossing.feature_chat.presentation.private_chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_chat.presentation.private_chat.components.ChatBottomBar
import com.app.thingscrossing.feature_chat.presentation.private_chat.components.CompanionProfileTopBar
import com.app.thingscrossing.feature_chat.presentation.private_chat.components.MessageList

@Composable
fun PrivateChatScreen(
    viewModel: PrivateChatViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState = viewModel.uiState

    if (uiState.errorMessageId != null) {
        ErrorDialog(
            onDismissError = { viewModel.onEvent(ChatEvent.DismissError) },
            errorMessageId = uiState.errorMessageId
        )
        return
    }

    Scaffold(
        topBar = {
            CompanionProfileTopBar(
                profile = uiState.companionUserProfile,
                isLoading = uiState.isLoading,
                navController = navController,
            )
        },
        bottomBar = {
            ChatBottomBar(
                message = uiState.newMessage,
                onMessageChange = { message ->
                    viewModel.onEvent(
                        ChatEvent.EditMessageChange(message)
                    )
                },
                onMessageSend = {
                    viewModel.onEvent(ChatEvent.SendMessage)
                },
            )
        }
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            MessageList(
                messages = uiState.messages,
                me = viewModel.userProfile,
                isLoading = uiState.isLoading
            )
        }
    }
}