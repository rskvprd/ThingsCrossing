package com.app.thingscrossing.feature_account.domain.repository

import com.app.thingscrossing.feature_account.domain.model.Credentials

interface AccountRepository {
    suspend fun registerWithCredentials(credentials: Credentials) : String
}