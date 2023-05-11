package com.app.thingscrossing.feature_account.data.repository

import com.app.thingscrossing.feature_account.data.remote.AccountApi
import com.app.thingscrossing.feature_account.data.remote.dto.SignUpResponse
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val api: AccountApi) : AccountRepository {
    override suspend fun registerUser(user: User): SignUpResponse =
        withContext(Dispatchers.IO) {
            api.registerUser(user)
        }
}