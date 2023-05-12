package com.app.thingscrossing.feature_account.domain.use_case

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.data.local.AUTH_KEY
import com.app.thingscrossing.feature_account.data.local.authDataStore
import com.app.thingscrossing.feature_account.data.remote.dto.Token
import com.app.thingscrossing.feature_account.data.remote.dto.UserProfile
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetCurrentUserProfileByTokenUseCase(
    private val context: Context,
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(): Flow<Resource<UserProfile>> = flow {
        emit(Resource.Loading())
        val authKey: String = context.authDataStore.data.first()[AUTH_KEY]!!
        emitAll(
            Resource.defaultHandleApiResource(onHttpException = {
                context.authDataStore.edit { it.clear() }
                emit(Resource.Error(R.string.user_with_this_auth_key_does_not_exist))
            }) {
                accountRepository.getUserProfileByAuthToken(Token(token = authKey))
            }
        )

    }
}
