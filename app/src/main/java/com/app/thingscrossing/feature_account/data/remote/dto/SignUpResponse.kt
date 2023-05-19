package com.app.thingscrossing.feature_account.data.remote.dto

import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.model.UserProfile

data class SignUpResponse(
    val token: String,
    val user: User,
    val profile: UserProfile
)