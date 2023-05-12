package com.app.thingscrossing.feature_account.data.remote.dto

import com.app.thingscrossing.feature_account.domain.model.User

data class UserProfile(
    val id: Int,
    val avatar: String,
    val user: User
)