package com.app.thingscrossing.feature_chat.data.remote.request

data class SendMessageRequest(
    val text: String,
    val room: Int,
)