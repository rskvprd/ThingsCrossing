package com.app.thingscrossing.feature_chat.domain.model

import com.app.thingscrossing.feature_account.domain.model.UserProfile

data class Message(
    val id: Int,
    val room: Int,
    val sentDateTime: String,
    val text: String,
    val toUser: UserProfile,
    val fromUser: UserProfile
)