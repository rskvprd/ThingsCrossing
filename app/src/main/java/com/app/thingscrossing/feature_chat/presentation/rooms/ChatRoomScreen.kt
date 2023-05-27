package com.app.thingscrossing.feature_chat.presentation.rooms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.LoadingDialog
import com.app.thingscrossing.feature_chat.presentation.rooms.components.ChatRoomList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    currentUserProfile: UserProfile?,
    viewModel: ChatRoomViewModel,
) {
    val uiState = viewModel.uiState

    if (uiState.errorMessageId != null) {
        ErrorDialog(
            onDismissError = { viewModel.onEvent(ChatRoomEvent.DismissError) },
            errorMessageId = uiState.errorMessageId
        )
    }
    if (uiState.isLoading) {
        LoadingDialog(progression = null)
    }
    Scaffold(topBar = {
        TopAppBar(title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.padding(end = 5.dp),
                    imageVector = Icons.Default.Forum,
                    contentDescription = null
                )
                Text(text = stringResource(id = R.string.chat_room_screen_title))
            }

        })
    }) { paddingValues ->

        if (currentUserProfile == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                ) {
                    Text(
                        text = stringResource(R.string.sign_in_before_conversation),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
            return@Scaffold
        }

        Box(Modifier.padding(paddingValues)) {
            ChatRoomList(chatRooms = uiState.chatRooms)
        }
    }
}