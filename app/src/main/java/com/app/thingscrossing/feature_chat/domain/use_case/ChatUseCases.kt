package com.app.thingscrossing.feature_chat.domain.use_case

data class ChatUseCases(
    val getMessagesByRoom: GetMessagesByRoom,
    val getOrCreatePrivateRoom: GetOrCreatePrivateRoom,
    val getMyRooms: GetMyRooms,
    val sendMessage: SendMessage,
)