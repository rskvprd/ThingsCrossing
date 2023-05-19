package com.app.thingscrossing.feature_account.domain.use_case

import android.content.Context
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.data.local.AUTH_KEY
import com.app.thingscrossing.feature_account.data.local.authDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class GetAuthKeyUseCase(private val context: Context) {
    operator fun invoke(): Flow<Resource<String?>> = flow {
        emit(Resource.Loading())
        val authKey: String? = context.authDataStore.data.firstOrNull()?.get(AUTH_KEY)

        emit(Resource.Success(data = authKey))
    }
}

