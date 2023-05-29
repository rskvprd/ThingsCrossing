package com.app.thingscrossing.feature_account.data.repository

import com.app.thingscrossing.feature_account.data.remote.AccountApi
import com.app.thingscrossing.feature_account.data.remote.dto.SignUpResponse
import com.app.thingscrossing.feature_account.domain.model.Token
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val api: AccountApi) : AccountRepository {
    override suspend fun obtainAuthToken(user: User): Token =
        withContext(Dispatchers.IO) {
            api.obtainAuthToken(user)
        }

    override suspend fun registerUser(user: User): SignUpResponse =
        withContext(Dispatchers.IO) {
            api.registerUser(user)
        }

    override suspend fun getUserProfileByAuthToken(token: Token): UserProfile =
        withContext(Dispatchers.IO) {
            api.getUserProfileByToken(token)
        }

    override suspend fun getUserProfileById(id: Int): UserProfile =
        withContext(Dispatchers.IO) {
            api.getUserProfileById(id)
        }

    override suspend fun uploadAvatar(
        authKey: String,
        userId: Int,
        avatar: MultipartBody.Part
    ): UserProfile =
        withContext(Dispatchers.IO) {
            api.uploadAvatar(authKey = authKey, userId = userId, avatar = avatar)
        }

}