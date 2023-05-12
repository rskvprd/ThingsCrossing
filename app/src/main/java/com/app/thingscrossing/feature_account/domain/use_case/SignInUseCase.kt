package com.app.thingscrossing.feature_account.domain.use_case

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.data.local.AUTH_KEY
import com.app.thingscrossing.feature_account.data.local.authDataStore
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class SignInUseCase(
    private val context: Context,
    private val accountRepository: AccountRepository
) {

    operator fun invoke(user: User): Flow<Resource<String>> =
        flow {
            try {
                emit(Resource.Loading())
                val result = accountRepository.obtainAuthToken(user)
                context.authDataStore.edit { preferences ->
                    preferences[AUTH_KEY] = result.token
                }
                emit(Resource.Success(result.token))
            } catch (connectException: ConnectException) {
                emit(Resource.Error(R.string.server_refused_message))
            } catch (e: SocketTimeoutException) {
                emit(Resource.Error(R.string.server_timeout_message))
            } catch (e: HttpException) {
                emit(Resource.Error(R.string.incorrect_username_or_password))
            }
        }
}