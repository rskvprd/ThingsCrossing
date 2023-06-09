package com.app.thingscrossing.feature_chat.presentation.rooms.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun ChatRoomList(
    chatRooms: List<ChatRoom>,
    myProfile: UserProfile,
    onPrivateRoom: (companion: UserProfile, ChatRoom) -> Unit,
    isLoading: Boolean,
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    LazyColumn(
        modifier = Modifier.onGloballyPositioned {
            shimmer.updateBounds(it.boundsInWindow())
        },
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        content = {
            if (isLoading) {
                items(10) {
                    LoadingChatRoomItem(shimmer = shimmer)
                }
            } else {
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
            }
        })
}