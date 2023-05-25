package com.app.thingscrossing.feature_chat.domain.model

data class ChatRoom(
    val id: Int,
    val lastMessageDatetime: String,
    val name: String,
    val participants: List<Participant>,
    val type: String
)