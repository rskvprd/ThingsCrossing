package com.app.thingscrossing.feature_chat.domain.model

import com.app.thingscrossing.feature_account.domain.model.UserProfile

data class Participant(
    val id: Int,
    val participant: UserProfile,
    val room: Int
)