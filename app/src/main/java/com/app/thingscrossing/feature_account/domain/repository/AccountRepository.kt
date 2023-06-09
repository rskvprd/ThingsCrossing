package com.app.thingscrossing.feature_account.domain.repository

import com.app.thingscrossing.feature_account.data.remote.dto.SignUpResponse
import com.app.thingscrossing.feature_account.domain.model.Token
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import okhttp3.MultipartBody

interface AccountRepository {
    /** @return Auth token*/
    suspend fun obtainAuthToken(user: User): Token

    suspend fun registerUser(user: User): SignUpResponse

    suspend fun getUserProfileByAuthToken(token: Token): UserProfile

    suspend fun getUserProfileById(id: Int): UserProfile

    suspend fun uploadAvatar(authKey: String, userId: Int, avatar: MultipartBody.Part): UserProfile
}