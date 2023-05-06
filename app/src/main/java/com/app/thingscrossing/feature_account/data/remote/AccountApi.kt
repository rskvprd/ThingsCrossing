package com.app.thingscrossing.feature_account.data.remote

import com.app.thingscrossing.feature_account.domain.model.Credentials
import retrofit2.http.Body
import retrofit2.http.POST


interface AccountApi {
    @POST("obtain-auth-token/")
    suspend fun registerWithCredentials(@Body credentials: Credentials) : String
}