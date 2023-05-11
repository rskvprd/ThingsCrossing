package com.app.thingscrossing.feature_account.domain.use_case

import android.util.Log
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.data.remote.dto.SignUpResponse
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class SignUpUseCase(private val accountRepository: AccountRepository) {
    /**
     * @return Auth token if registration is successful*/
    operator fun invoke(user: User): Flow<Resource<SignUpResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                val result = accountRepository.registerUser(user)
                emit(Resource.Success(result))
            } catch (connectException: ConnectException) {
                emit(Resource.Error(R.string.server_refused_message))
            } catch (e: SocketTimeoutException) {
                emit(Resource.Error(R.string.server_timeout_message))
            } catch (e: HttpException) {
                Log.d("REGISTRATION_MESSAGE", e.message())
                Log.d("REGISTRATION_RESPONSE", e.response().toString())
                emit(Resource.Error(R.string.http_exception_message))
            }
        }
}