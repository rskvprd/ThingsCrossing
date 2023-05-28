package com.app.thingscrossing.feature_chat.presentation.rooms.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.thingscrossing.core.globalUrl
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import java.time.format.DateTimeFormatter

@Composable
fun PrivateRoomItem(
    modifier: Modifier = Modifier,
    chatRoom: ChatRoom,
    me: UserProfile,
    onRoomClick: (companion: UserProfile, ChatRoom) -> Unit,
) {
    val companion =
        chatRoom.participants.filter { it.participant.id != me.id }[0].participant

    Box(
        modifier = modifier.clickable {
            onRoomClick(companion, chatRoom)
        },
    ) {
        Box(modifier = Modifier.padding(20.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Row {
                    AsyncImage(
                        model = companion.avatar.globalUrl(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = companion.user.fullName,
                            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
                        )
                        if (chatRoom.lastMessage != null) {
                            Text(
                                text = chatRoom.lastMessage.text.crop(20),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
                            )
                        } else {
                            Box(modifier = Modifier)
                        }
                    }
                }
                if (chatRoom.lastMessage != null) {
                    Text(
                        text = chatRoom.lastMessage.sentDateTime.toLocalTime().format(
                            DateTimeFormatter.ofPattern("HH:mm")
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
                    )

                } else {
                    Box(Modifier)
                }
            }
        }
        Divider(modifier = Modifier.align(Alignment.BottomEnd))
    }
}

/**Crop string to [size] symbols and add "..."*/
private fun String.crop(size: Int = 20): String {
    if (this.length > size) return this.slice(IntRange(0, size - 4)) + "..."
    return this
}