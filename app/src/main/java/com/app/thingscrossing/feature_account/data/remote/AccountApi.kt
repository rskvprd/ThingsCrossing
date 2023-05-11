package com.app.thingscrossing.feature_account.data.remote

import com.app.thingscrossing.feature_account.data.remote.dto.SignUpResponse
import com.app.thingscrossing.feature_account.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST


interface AccountApi {
    @POST("obtain-auth-token/")
    suspend fun obtainAuthToken(@Body user: User): String

    @POST("register/")
    suspend fun registerUser(@Body user: User): SignUpResponse
}