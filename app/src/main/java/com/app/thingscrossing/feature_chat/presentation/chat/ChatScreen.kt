package com.app.thingscrossing.feature_chat.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.LoadingDialog
import com.app.thingscrossing.feature_chat.presentation.chat.components.CompanionProfileTopBar
import com.app.thingscrossing.feature_chat.presentation.chat.components.MessageList

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    navController: NavController,
    currentUserProfile: UserProfile,
) {
    val uiState = viewModel.uiState

    if (uiState.errorMessageId != null) {
        ErrorDialog(
            onDismissError = { viewModel.onEvent(ChatEvent.DismissError) },
            errorMessageId = uiState.errorMessageId
        )
        return
    }

    if (uiState.isLoading) {
        LoadingDialog(progression = null)
        return
    }

    Scaffold(
        topBar = {
            CompanionProfileTopBar(profile = uiState.companionUserProfile!!)
        },
        bottomBar = {
            TextField(value = "", onValueChange = {  })
        }
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {
            uiState.messages.map {
                MessageList(
                    messages = emptyList(),
                    me = currentUserProfile
                )
            }
        }
    }
}