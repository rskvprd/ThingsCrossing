package com.app.thingscrossing.feature_account.domain.repository

import com.app.thingscrossing.feature_account.data.remote.dto.SignUpResponse
import com.app.thingscrossing.feature_account.domain.model.User

interface AccountRepository {
    suspend fun registerUser(user: User) : SignUpResponse
}