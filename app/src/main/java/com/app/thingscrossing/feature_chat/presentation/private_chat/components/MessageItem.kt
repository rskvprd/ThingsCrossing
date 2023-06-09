package com.app.thingscrossing.feature_chat.presentation.private_chat.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.feature_chat.domain.model.Message
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer
import java.time.format.DateTimeFormatter

@Composable
fun MessageItem(
    message: Message,
    isMe: Boolean,
) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .align(
                    if (isMe) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    }
                )
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .widthIn(max = 300.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            )
        ) {
            Box(
                Modifier
                    .padding(5.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(
                        top = 5.dp,
                        bottom = 25.dp,
                        end = 5.dp,
                        start = 10.dp
                    )
                )
                Text(
                    text = message.sentDateTime.toLocalTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm")),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}

@Composable
fun LoadingMessageItem(isMe: Boolean, shimmer: Shimmer) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .align(
                    if (isMe) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    }
                )
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .width(300.dp)
                .height(100.dp)
                .shimmer(shimmer),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            )
        ) {
        }
    }
}