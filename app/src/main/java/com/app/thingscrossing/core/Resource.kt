package com.app.thingscrossing.core

import androidx.annotation.StringRes
import com.app.thingscrossing.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

/** Remote resource from API with Success Error and Loading states */
sealed class Resource<T>(val data: T? = null, val messageId: Int? = null, val progression: Int? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(@StringRes messageId: Int, data: T? = null) : Resource<T>(data, messageId)
    class Loading<T>(data: T? = null, progression: Int? = null) : Resource<T>(data, progression)

    companion object {
        fun <T> handleResource(request: suspend () -> T): Flow<Resource<T>> =
            flow {
                try {
                    emit(Loading())
                    val result = request()
                    emit(Success(result))
                } catch (connectException: ConnectException) {
                    emit(Error(R.string.server_refused_message))
                } catch (e: SocketTimeoutException) {
                    emit(Error(R.string.server_timeout_message))
                } catch (e: HttpException) {
                    emit(Error(R.string.http_exception_message))
                }
            }
    }

}