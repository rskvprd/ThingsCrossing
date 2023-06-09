package com.app.thingscrossing.feature_chat.presentation.private_chat.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_chat.domain.model.Message
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun MessageList(
    messages: List<Message>,
    me: UserProfile,
    isLoading: Boolean,
) {
    if (messages.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(id = R.string.no_messages))
        }
    }
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    LazyColumn(
        Modifier
            .fillMaxSize()
            .onGloballyPositioned { shimmer.updateBounds(it.boundsInWindow()) },
    ) {
        if (isLoading) {
            items(10) {
                LoadingMessageItem(isMe = it % 2 == 0, shimmer = shimmer)
            }
        } else {
            items(messages.size) { index ->
                val message = messages[index]

                MessageItem(
                    message = message,
                    isMe = message.fromUser.id == me.id
                )
            }
        }

    }
}