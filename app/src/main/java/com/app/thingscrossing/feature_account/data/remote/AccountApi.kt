package com.app.thingscrossing.feature_account.data.remote

import com.app.thingscrossing.feature_account.data.remote.dto.UserProfile
import com.app.thingscrossing.feature_account.data.remote.dto.SignUpResponse
import com.app.thingscrossing.feature_account.data.remote.dto.Token
import com.app.thingscrossing.feature_account.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AccountApi {
    @POST("obtain-auth-token/")
    suspend fun obtainAuthToken(@Body user: User): Token

    @POST("register/")
    suspend fun registerUser(@Body user: User): SignUpResponse

    @POST("users/get-by-token/")
    suspend fun getUserProfileByToken(@Body token: Token): UserProfile
}